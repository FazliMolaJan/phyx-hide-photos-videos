package com.aganticsoft.phyxhidephotosandvideos.model;

import android.app.Application;
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

public class VideoLiveData extends LiveData<Resource<List<MediaModel>>> {

    private static final String[] VIDEO_PROJECTION_BUCKET = {
            MediaStore.Video.VideoColumns.BUCKET_ID,
            MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Video.VideoColumns.DISPLAY_NAME,
            MediaStore.Video.VideoColumns.DATA
    };

    private static final int BUCKETID_INDEX = 0;
    private static final int BUCKET_DISPLAYNAME_INDEX = 1;
    private static final int DISPLAY_NAME_INDEX = 2;
    private static final int DATA_INDEX = 3;

    private final Context mContext;
    private final VideoContentObserver mObserver;
    private Cursor mCursor;
    private boolean forceQuery;

    public VideoLiveData(@NonNull Application application) {
        super();
        mContext = application.getApplicationContext();
        mObserver = new VideoContentObserver();
    }

    private void loadData() {
        if (mCursor != null && !forceQuery
                && !mCursor.isClosed()) {
            return;
        }

        forceQuery = false;

        Cursor cursor = mContext.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                VIDEO_PROJECTION_BUCKET, null, null, MediaStore.Video.Media.DATE_TAKEN);

        List<MediaModel> videos = new ArrayList<>();

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
                            .mediaType(MediaModel.MediaType.TYPE_VIDEO)
                            .build();

                    Timber.d(model.toString());
                    videos.add(model);
                }

                if (this.mCursor != null)
                    this.mCursor.close();
                this.mCursor = cursor;

                setValue(Resource.success(videos));

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

    public final class VideoContentObserver
            extends ContentObserver {

        public VideoContentObserver() {
            super(new Handler(Looper.getMainLooper()));
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override
        public void onChange(boolean selfChange) {
            Timber.e("Video data has changed.");
            forceQuery = true;
            loadData();
        }
    }
}
