package com.edkornev.vkgallery;

import android.app.Application;

import com.vk.sdk.VKSdk;

/**
 * Created by Eduard on 10.11.2016.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Init vk
        VKSdk.initialize(getApplicationContext());
    }
}
