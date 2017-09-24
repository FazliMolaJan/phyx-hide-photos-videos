package com.aganticsoft.phyxhidephotosandvideos.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.aganticsoft.phyxhidephotosandvideos.model.MediaModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by ttson
 * Date: 9/24/2017.
 */

public class MediaLoader {

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

    private static final String[] VIDEO_PROJECTION_BUCKET = {
            MediaStore.Video.VideoColumns.BUCKET_ID,
            MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Video.VideoColumns.DISPLAY_NAME,
            MediaStore.Video.VideoColumns.DATA
    };



    public static List<MediaModel> loadImages(Context context) {
        Cursor c = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION_BUCKET
                , null, null, MediaStore.Images.Media.DATE_TAKEN);

        List<MediaModel> images = new ArrayList<>();

        try {
            while (c.moveToNext()) {
                MediaModel model = MediaModel.builder()
                        .bucketId(c.getInt(BUCKETID_INDEX))
                        .bucketName(c.getString(BUCKET_DISPLAYNAME_INDEX))
                        .displayName(c.getString(DISPLAY_NAME_INDEX))
                        .bucketUrl(c.getString(DATA_INDEX))
                        .mediaType(MediaModel.MediaType.TYPE_IMAGE)
                        .build();

                Timber.d(model.toString());
                images.add(model);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            c.close();
        }

        return images;
    }

    public static List<MediaModel> loadVideos(Context context) {
        Cursor c = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                VIDEO_PROJECTION_BUCKET, null, null, MediaStore.Video.Media.DATE_TAKEN);

        List<MediaModel> videos = new ArrayList<>();

        try {
            while (c.moveToNext()) {
                MediaModel model = MediaModel.builder()
                        .bucketId(c.getInt(BUCKETID_INDEX))
                        .bucketName(c.getString(BUCKET_DISPLAYNAME_INDEX))
                        .displayName(c.getString(DISPLAY_NAME_INDEX))
                        .bucketUrl(c.getString(DATA_INDEX))
                        .mediaType(MediaModel.MediaType.TYPE_VIDEO)
                        .build();

                Timber.d(model.toString());
                videos.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }

        return videos;
    }
}
