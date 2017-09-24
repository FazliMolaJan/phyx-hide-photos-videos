package com.aganticsoft.phyxhidephotosandvideos.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.aganticsoft.phyxhidephotosandvideos.model.MediaModel;
import com.aganticsoft.phyxhidephotosandvideos.model.Resource;
import com.aganticsoft.phyxhidephotosandvideos.repository.MediaRepositry;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by ttson
 * Date: 9/24/2017.
 */

public class MediaViewModel extends ViewModel {
    private final MutableLiveData<Integer> triggerLoadImages = new MutableLiveData<>();
    private final MutableLiveData<Integer> triggerLoadVideos = new MutableLiveData<>();

    private final LiveData<Resource<List<MediaModel>>> images;
    private final LiveData<Resource<List<MediaModel>>> videos;

    @Inject
    public MediaViewModel(MediaRepositry repositry) {
        images = Transformations.switchMap(triggerLoadImages, dummyInt -> repositry.loadImages());
        videos = Transformations.switchMap(triggerLoadVideos, dummyInt -> repositry.loadVideos());
    }

    public void requestImages() {
        triggerLoadImages.setValue(0);
    }

    public void requestVideos() {
        Timber.e("onRequest Videos");

        triggerLoadVideos.setValue(0);
    }

    public LiveData<Resource<List<MediaModel>>> getImages() { return images; }

    public LiveData<Resource<List<MediaModel>>> getVideos() { return videos; }
}
