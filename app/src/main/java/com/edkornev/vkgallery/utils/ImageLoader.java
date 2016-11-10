package com.edkornev.vkgallery.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
            Future<Bitmap> future = mExecutorService.submit(new LoadRunnable(url));

            try {
                image = future.get();

                mMemoryCache.put(url, image);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        ivContent.setImageBitmap(image);
    }

    private class LoadRunnable implements Callable<Bitmap> {

        private String mUrl;

        public LoadRunnable(String url) {
            this.mUrl = url;
        }

        @Override
        public Bitmap call() throws Exception {
            Bitmap image = null;

            try {
                OkHttpClient httpClient = new OkHttpClient();
                Request request = new Request.Builder().url(mUrl).get().build();
                Call call = httpClient.newCall(request);
                Response response = call.execute();

                InputStream is = response.body().byteStream();
                image = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return image;
        }
    }
}
