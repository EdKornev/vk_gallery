package com.edkornev.vkgallery.ui.base.activities;

import android.app.Activity;
import android.app.AlertDialog;

import com.edkornev.vkgallery.R;

/**
 * Created by Eduard on 10.11.2016.
 */
public class BaseActivity extends Activity {

    private AlertDialog mErrorDialog;

    /**
     * Show error dialog
     * @param message
     */
    protected void showErrorDialog(String message) {
        if (mErrorDialog == null) {
            mErrorDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.error_message)
                    .setMessage(message)
                    .create();
        }

        if (!mErrorDialog.isShowing()) {
            mErrorDialog.show();
        }
    }
}
