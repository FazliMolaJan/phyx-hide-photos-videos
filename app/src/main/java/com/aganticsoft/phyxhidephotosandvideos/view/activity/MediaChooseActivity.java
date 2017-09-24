package com.aganticsoft.phyxhidephotosandvideos.view.activity;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aganticsoft.phyxhidephotosandvideos.AppInjector;
import com.aganticsoft.phyxhidephotosandvideos.PhyxApp;
import com.aganticsoft.phyxhidephotosandvideos.R;
import com.aganticsoft.phyxhidephotosandvideos.model.MediaModel;
import com.aganticsoft.phyxhidephotosandvideos.model.Resource;
import com.aganticsoft.phyxhidephotosandvideos.view.fragment.BaseFragment;
import com.aganticsoft.phyxhidephotosandvideos.viewmodel.MediaViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

public class MediaChooseActivity extends BaseActivity implements HasSupportFragmentInjector {

    private int mediaType;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private MediaViewModel mediaViewModel;

    public static Intent getIntent(Context context, @MediaModel.MediaType int mediaType) {
        Intent intent = new Intent(context, MediaChooseActivity.class);
        intent.putExtra("type", mediaType);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_photo);
        ButterKnife.bind(this);

        mediaType = getIntent().getIntExtra("type", MediaModel.MediaType.TYPE_IMAGE);

        initToolbar();
        initData();
    }



    private void initData() {
        Timber.e("initData");

        mediaViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MediaViewModel.class);
        mediaViewModel.getImages().observe(this, this::onResourceChanged);
        mediaViewModel.getVideos().observe(this, this::onResourceChanged);

        mediaViewModel.requestVideos();
    }

    public void onResourceChanged(Resource<List<MediaModel>> resourceList) {
        if (resourceList.status == Resource.Status.SUCESS) {
            Timber.e("Loading image sucess");
            for (MediaModel model : resourceList.data) {
                Timber.e(model.toString());
            }
        } else if (resourceList.status == Resource.Status.LOADING) {
            Timber.e("Loading image...");
        } else { // error
            Timber.e("Loading image error");
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);

        if (mediaType == MediaModel.MediaType.TYPE_IMAGE) {
            toolbar.setTitle("Choose images to hdie");
        } else {
            toolbar.setTitle("Choose videos to hide");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
