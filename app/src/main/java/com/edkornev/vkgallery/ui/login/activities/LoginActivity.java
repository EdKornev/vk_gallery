package com.edkornev.vkgallery.ui.login.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.edkornev.vkgallery.R;
import com.edkornev.vkgallery.ui.base.activities.BaseActivity;
import com.edkornev.vkgallery.ui.gallery.activities.GalleryActivity;
import com.edkornev.vkgallery.utils.PreferenceUtils;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class LoginActivity extends BaseActivity implements View.OnClickListener, VKCallback<VKAccessToken> {

    private static final String PHOTO_SCOPE = "photos";
    private static final String FRIEND_SCOPE = "friends";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, this)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        VKSdk.login(this, PHOTO_SCOPE, FRIEND_SCOPE);
    }

    /* VK Callbacks */
    @Override
    public void onResult(VKAccessToken res) {
        PreferenceUtils.getPref(this)
                .edit()
                .putString(getString(R.string.key_settings_access_token), res.accessToken)
                .apply();

        Intent activity = new Intent(this, GalleryActivity.class);
        startActivity(activity);

        finish();
    }

    @Override
    public void onError(VKError error) {
        super.showErrorDialog(error.errorMessage);
    }
}
