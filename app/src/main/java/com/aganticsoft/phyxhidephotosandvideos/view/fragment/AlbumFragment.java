package com.aganticsoft.phyxhidephotosandvideos.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aganticsoft.phyxhidephotosandvideos.R;
import com.aganticsoft.phyxhidephotosandvideos.di.Injectable;
import com.aganticsoft.phyxhidephotosandvideos.model.MediaModel;
import com.aganticsoft.phyxhidephotosandvideos.util.PrefManager;
import com.aganticsoft.phyxhidephotosandvideos.view.activity.MediaChooseActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

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
    @BindView(R.id.multiple_actions)
    FloatingActionsMenu fabMenus;

    public static final int REQUEST_CHOOSE_IMAGES = 0x91;
    public static final int REQUEST_CHOOSE_VIDEOS = 0x92;


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    // <editor-fold desc="[ =============== BIND CLICK ===================]">

    @OnClick(R.id.fabAddPhoto)
    public void onClickAddPhoto() {
        startActivityForResult(MediaChooseActivity.getIntent(mContext
                , MediaModel.MediaType.TYPE_IMAGE), REQUEST_CHOOSE_IMAGES);

        fabMenus.collapse();
    }

    @OnClick(R.id.fabAddVideo)
    public void onClickAddVideo() {
        startActivityForResult(MediaChooseActivity.getIntent(mContext
                , MediaModel.MediaType.TYPE_VIDEO), REQUEST_CHOOSE_VIDEOS);
        fabMenus.collapse();
    }

    @OnClick(R.id.fabAddAlbum)
    public void onClickAddAlbum() {
        fabMenus.collapse();
    }

    // </editor-fold>
}
