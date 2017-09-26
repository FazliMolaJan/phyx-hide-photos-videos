package com.aganticsoft.phyxhidephotosandvideos.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aganticsoft.phyxhidephotosandvideos.R;
import com.aganticsoft.phyxhidephotosandvideos.model.MediaModel;
import com.aganticsoft.phyxhidephotosandvideos.view.fragment.MediaPickerFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ttson
 * Date: 9/25/2017.*
 */

public class MediaPickerAdapter extends RecyclerView.Adapter<MediaPickerAdapter.MediaViewHolder> {
    private Context mContext;
    private String albumName;
    private @MediaModel.MediaType int mediaType;
    private List<MediaModel> medias;
    private Set<Integer> selectedItems = new HashSet<>();


    public MediaPickerAdapter(Context context, String albumName, @MediaModel.MediaType int mediaType, List<MediaModel> medias) {
        this.mContext = context;
        this.albumName = albumName;
        this.medias = medias;
        this.mediaType = mediaType;
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_mediapicker, parent, false);

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        RecyclerView.LayoutParams rm = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        rm.width = width / MediaPickerFragment.NUMBER_OF_COLUMN - 10;
        rm.height = rm.width;

        rm.leftMargin = 5;
        rm.rightMargin = 5;
        rm.topMargin = 5;
        rm.bottomMargin = 5;

        return new MediaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MediaViewHolder holder, int position) {
        MediaModel item = medias.get(position);

        if (item.mediaType() == MediaModel.MediaType.TYPE_IMAGE) {
            Glide.with(mContext)
                    .load(item.bucketUrl())
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.ivImage);
            holder.ivVideo.setVisibility(View.GONE);
        } else { // TYPE_VIDEO
            Glide.with(mContext)
                    .asBitmap()
                    .load(Uri.fromFile(new File(item.bucketUrl())))
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.ivImage);
            holder.ivVideo.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(view -> {
            if (selectedItems.contains(position)) {
                selectedItems.remove(position);
                notifyItemChanged(position);
            } else {
                selectedItems.add(position);
                notifyItemChanged(position);
            }
        });
        holder.ivZoomView.setOnClickListener(v -> {

        });

        if (selectedItems.contains(position)) {
            // selected
            holder.checkView.setVisibility(View.VISIBLE);
        } else {
            holder.checkView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return medias.size();
    }

    public void setData(List<MediaModel> medias, String albumName) {
        this.medias = medias;
        this.albumName = albumName;

        notifyDataSetChanged();
    }

    public List<MediaModel> getSelectedItems() {
        List<MediaModel> results = new ArrayList<>();

        for (Integer pos : selectedItems)
            results.add(medias.get(pos));

        return results;
    }

    public class MediaViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.rlCheckView)
        View checkView;
        @BindView(R.id.ivZoomView)
        ImageView ivZoomView;
        @BindView(R.id.ivVideo)
        ImageView ivVideo;


        public MediaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
