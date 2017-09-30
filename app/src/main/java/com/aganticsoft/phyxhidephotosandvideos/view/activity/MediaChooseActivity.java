package com.aganticsoft.phyxhidephotosandvideos.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aganticsoft.phyxhidephotosandvideos.R;
import com.aganticsoft.phyxhidephotosandvideos.model.MediaModel;
import com.aganticsoft.phyxhidephotosandvideos.view.fragment.ChooseAlbumFragment;
import com.aganticsoft.phyxhidephotosandvideos.view.fragment.MediaPickerFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Two type of fragment inside: {@link ChooseAlbumFragment}, {@link MediaPickerFragment}
 * <br/>Return type:
 * <br/><t></t>RESULT_OK: Intent data with key "data": list of {@link MediaModel}
 * <br/><t></t>RESULT_CANCEL
 */
public class MediaChooseActivity extends BaseActivity implements HasSupportFragmentInjector {

    private int albumType;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    // After choose an album
    private boolean isSelectingMedia;
    private Menu menu;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private ChooseAlbumFragment frgChooseAlbum;
    private MediaPickerFragment mediaPickerFragment;

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

        albumType = getIntent().getIntExtra("type", MediaModel.MediaType.TYPE_IMAGE);

        initToolbar();
        initFragment();
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            setResult(RESULT_CANCELED);
            finish();
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;

        changeOptionsMenu();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_checkall:
                mediaPickerFragment.toggleCheckAll();
                break;
        }

        return true;
    }

    /**
     * Change option menu based on {@link MediaChooseActivity#isSelectingMedia}
     */
    public void changeOptionsMenu() {
        if (isSelectingMedia) {
            getMenuInflater().inflate(R.menu.menu_check_all, menu);
        } else {
            menu.clear();
        }
    }


    private void initFragment() {
        frgChooseAlbum = ChooseAlbumFragment.getInstance(albumType);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, frgChooseAlbum)
                .addToBackStack(ChooseAlbumFragment.class.getSimpleName())
                .commit();
    }

    private void changeMode(boolean isChooseAlbum) {
        if (isChooseAlbum) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, frgChooseAlbum)
                    .addToBackStack(ChooseAlbumFragment.class.getSimpleName())
                    .commit();

            isSelectingMedia = false;

            if (albumType == MediaModel.MediaType.TYPE_IMAGE) {
                getSupportActionBar().setTitle("Choose images to hide");
            } else {
                getSupportActionBar().setTitle("Choose videos to hide");
            }

        } else {
            if (mediaPickerFragment == null)
                return;

            isSelectingMedia = true;

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, mediaPickerFragment)
                    .addToBackStack(MediaPickerFragment.class.getSimpleName())
                    .commit();

            getSupportActionBar().setTitle("0 items selected");
        }

        changeOptionsMenu();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);

        if (albumType == MediaModel.MediaType.TYPE_IMAGE) {
            getSupportActionBar().setTitle("Choose images to hide");
        } else {
            getSupportActionBar().setTitle("Choose videos to hide");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> {
            if (isSelectingMedia)
            {
                isSelectingMedia = false;


                if (albumType == MediaModel.MediaType.TYPE_IMAGE) {
                    getSupportActionBar().setTitle("Choose images to hide");
                } else {
                    getSupportActionBar().setTitle("Choose videos to hide");
                }

                changeOptionsMenu();
            }

            onBackPressed();
        });
    }

    /**
     * When 1 album to be clicked and then we need to move {@link MediaPickerFragment}
     * to choose media items
     * <br/>
     * Get call from {@link ChooseAlbumFragment#onAlbumChooseClick(String, List)}
     * @param albumName Album Name
     * @param medias List of items in that album
     */
    public void onAlbumChooseClick(String albumName, List<MediaModel> medias) {
        mediaPickerFragment = MediaPickerFragment.newInstance(albumType, albumName, medias);

        changeMode(false);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    /**
     * This method be called when user choose an media item from {@link MediaPickerFragment#onItemSelectChanged(int, MediaModel)}
     * @param num Number of item
     * @param item Item clicked
     */
    public void onItemChanged(int num, MediaModel item) {
        getSupportActionBar().setTitle(num + " items selected");
    }

    /**
     * This method be called when user click import button {@link MediaPickerFragment#onImportClicked()}
     * @param mediaModels List of media items want to import
     */
    public void onRequestImport(List<MediaModel> mediaModels) {
        Intent data = new Intent();
        data.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) mediaModels);

        setResult(RESULT_OK);
    }
}
