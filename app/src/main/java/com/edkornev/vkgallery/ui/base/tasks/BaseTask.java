package com.edkornev.vkgallery.ui.base.tasks;

import android.os.AsyncTask;

/**
 * Created by kornev on 12/11/16.
 */
public abstract class BaseTask<T,K,V> extends AsyncTask<T,K,V> {

    protected TaskListener<V> mTaskListener;

    public BaseTask(TaskListener<V> taskListener) {
        this.mTaskListener = taskListener;
    }

    public interface TaskListener<C> {
        void onSuccess(C response);
        void onError(int resId);
    }
}
