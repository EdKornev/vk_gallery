package com.edkornev.vkgallery.ui.preview.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edkornev.vkgallery.R;
import com.edkornev.vkgallery.utils.ImageLoader;
import com.edkornev.vkgallery.utils.api.models.response.PhotoResponse;

/**
 * Created by kornev on 11/11/16.
 */
public class PreviewPhotoFragment extends Fragment {

    public static final String KEY_BUNDLE_PHOTO = "photo";

    private PhotoResponse mPhoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPhoto = getArguments().getParcelable(KEY_BUNDLE_PHOTO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview_photo, parent, false);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        ImageView ivPhoto = (ImageView) view.findViewById(R.id.iv_photo);

        tvTitle.setText(mPhoto.getText());

        if (mPhoto.getPhoto_1280() != null) {
            ImageLoader.getInstance().loadImage(mPhoto.getPhoto_1280(), ivPhoto);
        } else {
            ImageLoader.getInstance().loadImage(mPhoto.getPhoto_604(), ivPhoto);
        }

        return view;
    }
}
