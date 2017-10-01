package com.aganticsoft.phyxhidephotosandvideos.model;

import com.google.auto.value.AutoValue;

/**
 * Created by ttson
 * Date: 10/1/2017.
 */

@AutoValue
public abstract class PFile {
    public abstract String fileName();
    public abstract String path();
    public abstract String size();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder fileName(String value);
        public abstract Builder path(String value);
        public abstract Builder size(String value);
        public abstract PFile build();
    }

    public static Builder builder() { return new AutoValue_PFile.Builder(); }
}
