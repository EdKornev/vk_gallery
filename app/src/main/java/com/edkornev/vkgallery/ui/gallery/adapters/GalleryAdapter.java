package com.edkornev.vkgallery.ui.gallery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.edkornev.vkgallery.R;
import com.edkornev.vkgallery.ui.gallery.presenters.GalleryPresenter;
import com.edkornev.vkgallery.utils.ImageLoader;
import com.edkornev.vkgallery.utils.api.models.response.PhotoResponse;

/**
 * Created by Eduard on 10.11.2016.
 */
public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private GalleryPresenter mPresenter;
    private LayoutInflater mInflater;

    public GalleryAdapter(Context context, GalleryPresenter presenter) {
        this.mContext = context;
        this.mPresenter = presenter;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder baseHolder, int position) {
        ViewHolder holder = (ViewHolder) baseHolder;

        PhotoResponse response = mPresenter.getPhotos().get(position);

        ImageLoader.getInstance().loadImage(response.getPhoto_604(), holder.mIVPhoto);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getPhotos().size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mIVPhoto;

        public ViewHolder(View view) {
            super(view);

            mIVPhoto = (ImageView) view.findViewById(R.id.iv_photo);
            mIVPhoto.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mPresenter.clickPhoto(getAdapterPosition());
        }
    }
}
