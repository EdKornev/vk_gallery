package com.edkornev.vkgallery.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
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

    private MemoryCache mMemoryCache = new MemoryCache();
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(5);
    private Map<String, ImageView> mImageViewMap = new HashMap<>();

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
        Bitmap image = mMemoryCache.get(url);

        if (image == null) {
            mImageViewMap.put(url, ivContent);

            mExecutorService.execute(new LoadRunnable(url, mHandler));
            return;
        }

        ivContent.setImageBitmap(image);
    }

    public void stop() {
        mExecutorService.shutdownNow();
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String url = (String) message.obj;
            Bitmap image = mMemoryCache.get(url);
            ImageView ivContent = mImageViewMap.get(url);

            if (image != null) {
                ivContent.setImageBitmap(image);
            }

            mImageViewMap.remove(url);

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
            try {
                OkHttpClient httpClient = new OkHttpClient();
                Request request = new Request.Builder().url(mUrl).get().build();
                Call call = httpClient.newCall(request);
                Response response = call.execute();

                InputStream is = response.body().byteStream();
                Bitmap image = BitmapFactory.decodeStream(is);

                mMemoryCache.put(mUrl, image);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }

            Message message = mHandler.obtainMessage(1, mUrl);

            mHandler.sendMessage(message);
        }
    }
}
