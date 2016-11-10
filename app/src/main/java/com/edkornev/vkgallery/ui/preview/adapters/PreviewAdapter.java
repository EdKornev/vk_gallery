package com.edkornev.vkgallery.ui.preview.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.edkornev.vkgallery.ui.preview.fragments.PreviewPhotoFragment;
import com.edkornev.vkgallery.ui.preview.presenters.PreviewPresenter;

/**
 * Created by kornev on 11/11/16.
 */
public class PreviewAdapter extends FragmentPagerAdapter {

    private PreviewPresenter mPresenter;

    public PreviewAdapter(FragmentManager fm, PreviewPresenter presenter) {
        super(fm);

        this.mPresenter = presenter;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(PreviewPhotoFragment.KEY_BUNDLE_PHOTO, mPresenter.getPhotos().get(position));

        PreviewPhotoFragment fragment = new PreviewPhotoFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public int getCount() {
        return mPresenter.getPhotos().size();
    }
}
