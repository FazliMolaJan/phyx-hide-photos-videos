package com.aganticsoft.phyxhidephotosandvideos.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.aganticsoft.phyxhidephotosandvideos.AppExecutors;
import com.aganticsoft.phyxhidephotosandvideos.model.ImageLiveData;
import com.aganticsoft.phyxhidephotosandvideos.model.MediaModel;
import com.aganticsoft.phyxhidephotosandvideos.model.Resource;
import com.aganticsoft.phyxhidephotosandvideos.model.VideoLiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by ttson
 * Date: 9/24/2017.
 */

@Singleton
public class MediaRepositry {

    private final AppExecutors appExecutors;
    private final Application app;

    @Inject
    public MediaRepositry(Application app, AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        this.app = app;
    }

    public LiveData<Resource<List<MediaModel>>> loadImages() {
        return new ImageLiveData(app);
    }

    public LiveData<Resource<List<MediaModel>>> loadVideos() {
        return new VideoLiveData(app);
    }
}
