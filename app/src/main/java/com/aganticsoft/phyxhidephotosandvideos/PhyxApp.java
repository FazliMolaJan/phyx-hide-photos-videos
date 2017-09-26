package com.aganticsoft.phyxhidephotosandvideos;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;

import com.aganticsoft.phyxhidephotosandvideos.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

/**
 * Created by ttson
 * Date: 9/20/2017.
 */
public class PhyxApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private static PhyxApp instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        AppInjector.init(this);
        Timber.plant(new Timber.DebugTree());
    }

    public static synchronized PhyxApp getInstance() {
        return instance;
    }

    public void initDirectory() {
        File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Constants.APP_DIR);

        boolean isDirCreated = false;

        if (!appDir.exists()) {
            isDirCreated = appDir.mkdirs();
        } else {
            isDirCreated = true;
        }

        if (isDirCreated) {
            try {
                InputStream inputStream = getAssets().open("readme.txt");

                FileUtils.writeFile(appDir.getAbsolutePath() + File.separator + "/readme.txt", inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}