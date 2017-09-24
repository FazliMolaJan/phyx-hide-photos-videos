package com.aganticsoft.phyxhidephotosandvideos.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aganticsoft.phyxhidephotosandvideos.R;
import com.aganticsoft.phyxhidephotosandvideos.di.Injectable;
import com.aganticsoft.phyxhidephotosandvideos.model.MediaModel;
import com.aganticsoft.phyxhidephotosandvideos.util.MediaLoader;
import com.aganticsoft.phyxhidephotosandvideos.util.PrefManager;
import com.getbase.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ttson
 * Date: 9/22/2017.
 */

public class AlbumFragment extends BaseFragment implements Injectable {


    @BindView(R.id.fabAddFile)
    FloatingActionButton fabAddFile;
    @BindView(R.id.fabAddPhoto)
    FloatingActionButton fabAddPhoto;
    @BindView(R.id.fabAddVideo)
    FloatingActionButton fabAddVideo;
    @BindView(R.id.fabAddAlbum)
    FloatingActionButton fabAddAlbum;


    @Inject
    PrefManager prefManager;

    private Context mContext;

    public static AlbumFragment newInstance() {
        AlbumFragment frg = new AlbumFragment();
        Bundle bundle = new Bundle();

        frg.setArguments(bundle);

        return frg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_album, container, false);
        ButterKnife.bind(this , v);



        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    // <editor-fold desc="[ =============== BIND CLICK ===================]">

    @OnClick(R.id.fabAddPhoto)
    public void onClickAddPhoto() {
        MediaLoader.loadImages(mContext);
    }

    @OnClick(R.id.fabAddVideo)
    public void onClickAddVideo() {
        MediaLoader.loadVideos(mContext);
    }

    @OnClick(R.id.fabAddAlbum)
    public void onClickAddAlbum() {

    }

    // </editor-fold>
}
