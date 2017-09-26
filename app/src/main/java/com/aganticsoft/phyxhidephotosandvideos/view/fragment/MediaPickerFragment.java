package com.aganticsoft.phyxhidephotosandvideos.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aganticsoft.phyxhidephotosandvideos.R;
import com.aganticsoft.phyxhidephotosandvideos.adapter.MediaPickerAdapter;
import com.aganticsoft.phyxhidephotosandvideos.model.MediaModel;
import com.aganticsoft.phyxhidephotosandvideos.view.activity.MediaChooseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ttson
 * Date: 9/25/2017.
 */

public class MediaPickerFragment extends BaseFragment implements MediaPickerAdapter.MediaPickerListener {

    public static final int NUMBER_OF_COLUMN = 3;

    private Context mContext;
    private @MediaModel.MediaType int albumType;
    private String albumName;
    private List<MediaModel> medias;
    private MediaPickerAdapter mediaPickerAdapter;

    @BindView(R.id.rvMediaItem)
    RecyclerView rvMediaItem;
    @BindView(R.id.tvImport)
    TextView tvImport;

    public static MediaPickerFragment newInstance(@MediaModel.MediaType int albumType, String albumName
            , List<MediaModel> medias) {
        MediaPickerFragment frg = new MediaPickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", albumType);
        bundle.putString("album.name", albumName);
        bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) medias);

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
        View v = inflater.inflate(R.layout.fragment_media_picker, container, false);
        ButterKnife.bind(this , v);

        Bundle bundle = getArguments();
        if (bundle != null) {
            albumType = bundle.getInt("type", MediaModel.MediaType.TYPE_IMAGE);
            albumName = bundle.getString("album.name");
            medias = bundle.getParcelableArrayList("data");
        }

        mediaPickerAdapter = new MediaPickerAdapter(mContext, albumName, albumType, medias, this);

        GridLayoutManager glm = new GridLayoutManager(mContext, NUMBER_OF_COLUMN);
        rvMediaItem.setLayoutManager(glm);

        rvMediaItem.setAdapter(mediaPickerAdapter);

        return v;
    }

    @Override
    public void onItemSelectChanged(int num, @Nullable MediaModel item) {
        if (num > 0)
            tvImport.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        else
            tvImport.setTextColor(ContextCompat.getColor(mContext, R.color.geryCA));

        ((MediaChooseActivity) getActivity()).onItemChanged(num, item);
    }

    @OnClick(R.id.tvImport)
    public void onImportClicked() {
        if (mediaPickerAdapter.getSelectedItems().size() > 0)
            ((MediaChooseActivity) getActivity()).onRequestImport(mediaPickerAdapter.getSelectedItems());
    }

    /**
     * To be called from {@link MediaChooseActivity#onOptionsItemSelected(MenuItem)}
     */
    public void toggleCheckAll() {
        mediaPickerAdapter.toggleCheckAll();
    }
}
