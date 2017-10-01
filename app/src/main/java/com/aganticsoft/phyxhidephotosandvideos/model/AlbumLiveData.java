package com.aganticsoft.phyxhidephotosandvideos.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.FileObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aganticsoft.phyxhidephotosandvideos.Constants;

import java.util.List;

import timber.log.Timber;

/**
 * Created by ttson
 * Date: 10/1/2017.
 */

public class AlbumLiveData extends LiveData<List<PFile>> {
    private Application application;
    private FileObserver fileObserver;

    public AlbumLiveData(@NonNull Application application) {
        this.application = application;

        fileObserver = new FileObserver(Constants.PATH_APP_DIR) {
            @Override
            public void onEvent(int i, @Nullable String s) {
                onDirectoryFireEvent(i, s);
            }
        };
    }

    private void onDirectoryFireEvent(int type, String path) {
        switch (type) {
            case FileObserver.CREATE:
                Timber.e("folder monitor: create");
                break;
            case FileObserver.DELETE:
                Timber.e("folder monitor: delete");
                break;
            case FileObserver.MOVED_FROM:
                Timber.e("folder monitor: move from");
                break;
            case FileObserver.MOVED_TO:
                Timber.e("folder monitor: move to");
                break;
        }
    }

    @Override
    protected void onActive() {
        super.onActive();

        fileObserver.startWatching();
    }

    @Override
    protected void onInactive() {
        super.onInactive();

        fileObserver.stopWatching();
    }
}
