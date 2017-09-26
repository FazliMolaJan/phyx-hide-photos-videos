package com.aganticsoft.phyxhidephotosandvideos.model;

import android.os.Parcelable;
import android.support.annotation.IntDef;

import com.google.auto.value.AutoValue;

import static com.aganticsoft.phyxhidephotosandvideos.model.MediaModel.MediaType.TYPE_IMAGE;
import static com.aganticsoft.phyxhidephotosandvideos.model.MediaModel.MediaType.TYPE_VIDEO;

/**
 * Created by ttson
 * Date: 9/24/2017.
 */

@AutoValue
public abstract class MediaModel implements Parcelable {
    @IntDef(value = {TYPE_IMAGE, TYPE_VIDEO})
    public @interface MediaType {
        int TYPE_IMAGE = 0;
        int TYPE_VIDEO = 1;
    }

    public abstract int bucketId();
    public abstract String bucketName();
    public abstract String bucketUrl();
    public abstract String displayName();
    public abstract int mediaType();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder bucketId(int value);
        public abstract Builder bucketName(String value);
        public abstract Builder bucketUrl(String value);
        public abstract Builder displayName(String value);
        public abstract Builder mediaType(@MediaType int value);
        public abstract MediaModel build();
    }

    public static Builder builder() {
        return new AutoValue_MediaModel.Builder();
    }
}
