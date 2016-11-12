package com.edkornev.vkgallery.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.edkornev.vkgallery.utils.cache.LruMemoryCache;
import com.edkornev.vkgallery.utils.cache.MemoryCache;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Eduard on 10.11.2016.
 */
public class ImageLoader {

    private static final String TAG = ImageLoader.class.getSimpleName();

    private static ImageLoader instance;

    private OkHttpClient mHttpClient = new OkHttpClient();

    private MemoryCache mMemoryCache = new MemoryCache();
    private LruMemoryCache mLruMemoryCache = new LruMemoryCache();

    private ExecutorService mExecutorService = Executors.newFixedThreadPool(10);
    private ConcurrentHashMap<String, ImageView> mImageViewMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Bitmap> mPreCacheBitmapMap = new ConcurrentHashMap<>();

    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    public void loadImage(String url, ImageView ivContent) {
        Bitmap image = mLruMemoryCache.get(url);

        if (image == null) {
//            if (mImageViewMap.containsValue(ivContent)) {
//                for (Map.Entry<String, ImageView> entry : mImageViewMap.entrySet()) {
//                    if (ivContent.equals(entry.getValue())) {
//                        mImageViewMap.remove(entry.getKey());
//                        break;
//                    }
//                }
//            }

            mImageViewMap.put(url, ivContent);

            mExecutorService.execute(new LoadRunnable(url, mHandler));
            return;
        }

        ivContent.setImageBitmap(image);
    }

    public void stop() {
        // clear cache
        mLruMemoryCache.clear();

        // clear tasks
        mExecutorService.shutdownNow();
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String url = (String) message.obj;
            Bitmap image = mPreCacheBitmapMap.get(url);
            ImageView ivContent = mImageViewMap.get(url);

            if (image != null && ivContent != null) {
                ivContent.setImageBitmap(image);
            }

            mLruMemoryCache.put(url, image);

            mImageViewMap.remove(url);
            mPreCacheBitmapMap.remove(url);

            return true;
        }
    });

    private class LoadRunnable implements Runnable {

        private String mUrl;
        private Handler mHandler;

        public LoadRunnable(String url, Handler handler) {
            this.mUrl = url;
            this.mHandler = handler;
        }

        @Override
        public void run() {
            Call call = null;

            try {
                Request request = new Request.Builder().url(mUrl).get().build();
                call = mHttpClient.newCall(request);
                Response response = call.execute();

                InputStream is = response.body().byteStream();
                Bitmap image = BitmapFactory.decodeStream(is);

                is.close();
                response.close();
                mPreCacheBitmapMap.put(mUrl, image);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            } finally {
                if (call != null) {
                    call.cancel();
                }
            }

            Message message = mHandler.obtainMessage(1, mUrl);

            mHandler.sendMessage(message);
        }
    }
}
