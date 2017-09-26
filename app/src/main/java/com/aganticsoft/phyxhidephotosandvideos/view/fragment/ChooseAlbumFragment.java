package com.aganticsoft.phyxhidephotosandvideos.view.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aganticsoft.phyxhidephotosandvideos.R;
import com.aganticsoft.phyxhidephotosandvideos.adapter.ChooseAlbumAdapter;
import com.aganticsoft.phyxhidephotosandvideos.di.Injectable;
import com.aganticsoft.phyxhidephotosandvideos.model.MediaModel;
import com.aganticsoft.phyxhidephotosandvideos.model.Resource;
import com.aganticsoft.phyxhidephotosandvideos.view.activity.MediaChooseActivity;
import com.aganticsoft.phyxhidephotosandvideos.viewmodel.MediaViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by ttson
 * Date: 9/25/2017.
 */

public class ChooseAlbumFragment extends BaseFragment implements Injectable, ChooseAlbumAdapter.ChooseAlbumListener {
    @BindView(R.id.rvAlbum)
    RecyclerView rvAlbum;

    private @MediaModel.MediaType int albumType;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private MediaViewModel mediaViewModel;
    private ChooseAlbumAdapter albumAdapter;
    private Context mContext;

    public static ChooseAlbumFragment getInstance(@MediaModel.MediaType int albumType) {
        ChooseAlbumFragment frg = new ChooseAlbumFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", albumType);

        frg.setArguments(bundle);
        return frg;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        albumType = bundle.getInt("type");

        View view = inflater.inflate(R.layout.fragment_choose_album, container, false);
        ButterKnife.bind(this, view);

        albumAdapter = new ChooseAlbumAdapter(mContext, null, albumType, this);
        LinearLayoutManager llm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvAlbum.setLayoutManager(llm);
        rvAlbum.setAdapter(albumAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
    }

    private void initData() {
        Timber.e("initData");

        mediaViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MediaViewModel.class);
        if (albumType == MediaModel.MediaType.TYPE_IMAGE)
            mediaViewModel.getImages().observe(this, listResource ->
                onResourceChanged(listResource, MediaModel.MediaType.TYPE_IMAGE));
        if (albumType == MediaModel.MediaType.TYPE_VIDEO)
            mediaViewModel.getVideos().observe(this, listResource ->
                onResourceChanged(listResource, MediaModel.MediaType.TYPE_VIDEO));

        if (albumType == MediaModel.MediaType.TYPE_IMAGE) mediaViewModel.requestImages(); else mediaViewModel.requestVideos();
    }


    public void onResourceChanged(Resource<List<MediaModel>> resourceList, @MediaModel.MediaType int mediaType) {
        if (resourceList.status == Resource.Status.SUCESS) {

            List<MediaModel> data = resourceList.data;
            Map<String, List<MediaModel>> albumMap = new HashMap<>();

            for (MediaModel model : data) {
                if (albumMap.containsKey(model.bucketName())) {
                    List<MediaModel> list = albumMap.get(model.bucketName());

                    list.add(model);
                } else {
                    List<MediaModel> list = new ArrayList<>();
                    list.add(model);

                    albumMap.put(model.bucketName(), list);
                }
            }

            albumAdapter.setData(albumMap);
        } else if (resourceList.status == Resource.Status.LOADING) {
            Timber.e("Loading image...");
        } else { // error
            Timber.e("Loading image error");
        }
    }

    @Override
    public void onAlbumChooseClick(String albumName, List<MediaModel> medias) {
        ((MediaChooseActivity) getActivity()).onAlbumChooseClick(albumName, medias);
    }
}
