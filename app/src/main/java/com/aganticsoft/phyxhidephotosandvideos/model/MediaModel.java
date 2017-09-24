package com.aganticsoft.phyxhidephotosandvideos.model;

import android.support.annotation.IntDef;

import com.google.auto.value.AutoValue;

import static com.aganticsoft.phyxhidephotosandvideos.model.MediaModel.MediaType.TYPE_IMAGE;
import static com.aganticsoft.phyxhidephotosandvideos.model.MediaModel.MediaType.TYPE_VIDEO;

/**
 * Created by ttson
 * Date: 9/24/2017.
 */

@AutoValue
public abstract class MediaModel {
    @IntDef(value = {TYPE_IMAGE, TYPE_VIDEO})
    public @interface MediaType {
        int TYPE_IMAGE = 0;
        int TYPE_VIDEO = 1;
    }

    abstract int bucketId();
    abstract String bucketName();
    abstract String bucketUrl();
    abstract String displayName();
    abstract int mediaType();

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
