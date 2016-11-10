package com.edkornev.vkgallery.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.edkornev.vkgallery.R;

/**
 * Created by Eduard on 10.11.2016.
 */
public class PreferenceUtils {

    public static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(context.getString(R.string.key_settings_access_token), Context.MODE_PRIVATE);
    }
}
