package com.aganticsoft.phyxhidephotosandvideos.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by ttson
 * Date: 9/24/2017.
 */

public class ImageLiveData extends LiveData<Resource<List<MediaModel>>> {

    private static final String[] IMAGE_PROJECTION_BUCKET = {
            MediaStore.Images.ImageColumns.BUCKET_ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATA,
    };

    private static final int BUCKETID_INDEX = 0;
    private static final int BUCKET_DISPLAYNAME_INDEX = 1;
    private static final int DISPLAY_NAME_INDEX = 2;
    private static final int DATA_INDEX = 3;

    private final Context mContext;
    private final ImageContentObserver mObserver;
    private Cursor mCursor;

    public ImageLiveData(@NonNull Application application) {
        super();
        mContext = application.getApplicationContext();
        mObserver = new ImageContentObserver();
    }

    private void loadData() {
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION_BUCKET
                , null, null, MediaStore.Images.Media.DATE_TAKEN);

        List<MediaModel> images = new ArrayList<>();

        if (cursor != null) {
            try {
                // Ensure the cursor window is filled.
                cursor.getCount();
                cursor.registerContentObserver(mObserver);

                setValue(Resource.loading(null));


                while (cursor.moveToNext()) {
                    MediaModel model = MediaModel.builder()
                            .bucketId(cursor.getInt(BUCKETID_INDEX))
                            .bucketName(cursor.getString(BUCKET_DISPLAYNAME_INDEX))
                            .displayName(cursor.getString(DISPLAY_NAME_INDEX))
                            .bucketUrl(cursor.getString(DATA_INDEX))
                            .mediaType(MediaModel.MediaType.TYPE_IMAGE)
                            .build();

                    Timber.d(model.toString());
                    images.add(model);
                }

                if (this.mCursor != null)
                    this.mCursor.close();
                this.mCursor = cursor;

                setValue(Resource.success(images));

            } catch (RuntimeException ex) {
                setValue(Resource.error(ex.getMessage(), null));

                cursor.close();
                throw ex;
            }
        }
    }

    @Override
    protected void onActive() {
        super.onActive();

        loadData();
    }

    @Override
    protected void onInactive() {
        super.onInactive();


    }

    public final class ImageContentObserver
            extends ContentObserver {

        public ImageContentObserver() {
            super(new Handler(Looper.getMainLooper()));
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override
        public void onChange(boolean selfChange) {
            Timber.e("Image data has changed.");

            loadData();
        }
    }
}
