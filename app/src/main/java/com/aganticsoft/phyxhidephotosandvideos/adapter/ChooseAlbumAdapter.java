package com.aganticsoft.phyxhidephotosandvideos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aganticsoft.phyxhidephotosandvideos.R;
import com.aganticsoft.phyxhidephotosandvideos.model.MediaModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ttson
 * Date: 9/25/2017.
 */

public class ChooseAlbumAdapter extends RecyclerView.Adapter<ChooseAlbumAdapter.ViewHolder> {

    private Context context;
    private Map<String, List<MediaModel>> mapMedia;
    private List<String> albumNames;
    private @MediaModel.MediaType int albumType;

    public ChooseAlbumAdapter(Context context, Map<String, List<MediaModel>> mapMedia, @MediaModel.MediaType int albumType) {
        this.context = context;
        this.mapMedia = mapMedia;
        this.albumType = albumType;

        if (this.albumNames == null)
            this.albumNames = new ArrayList<>();
        else
            this.albumNames.clear();

        if (mapMedia  != null) {
            this.albumNames.addAll(this.mapMedia.keySet());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_choose_album, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<MediaModel> medias =  mapMedia.get(albumNames.get(position));
        holder.tvTitle.setText(albumNames.get(position));
        holder.tvNumItems.setText(medias.size() + " items");

        if (albumType == MediaModel.MediaType.TYPE_IMAGE) {
            Glide.with(context)
                    .load(medias.get(0).bucketUrl())
                    .apply(RequestOptions.fitCenterTransform())
                    .into(holder.ivThumbnail);
        } else {
            Glide
                    .with(context)
                    .asBitmap()
                    .load(Uri.fromFile(new File(medias.get(0).bucketUrl())))
                    .apply(RequestOptions.fitCenterTransform())
                    .into(holder.ivThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return mapMedia == null ? 0 : mapMedia.size();
    }

    public void setData(Map<String, List<MediaModel>> data) {
        this.mapMedia = data;

        if (this.albumNames == null)
            this.albumNames = new ArrayList<>();
        else
            this.albumNames.clear();

        if (mapMedia  != null) {
            this.albumNames.addAll(this.mapMedia.keySet());
        }

        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivThumbnail)
        ImageView ivThumbnail;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvItems)
        TextView tvNumItems;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
