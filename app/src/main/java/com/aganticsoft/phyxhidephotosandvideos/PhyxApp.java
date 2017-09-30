package com.aganticsoft.phyxhidephotosandvideos;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;

import com.aganticsoft.phyxhidephotosandvideos.util.FileUtils;
import com.aganticsoft.phyxhidephotosandvideos.view.activity.LockActivity;
import com.snatik.storage.EncryptConfiguration;
import com.snatik.storage.Storage;

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

    // dependencies
    private Storage storage;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        storage = new Storage(this);

        AppInjector.init(this);
        Timber.plant(new Timber.DebugTree());

        String pinCode = getSharedPreferences(Constants.MAIN_PREF, MODE_PRIVATE)
                .getString(Constants.PIN_CODE, "");


        if (!("".equals(pinCode))) {
            initStorageEncrypt(pinCode);
        }
    }
    public static synchronized PhyxApp getInstance() {
        return instance;
    }

    public void initDirectory() {
        File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Constants.APP_DIR_NAME);

        boolean isDirCreated = false;

        if (!appDir.exists()) {
            isDirCreated = appDir.mkdirs();
        } else {
            isDirCreated = true;
        }

        if (isDirCreated) {
            try {
                Constants.PATH_MAINALBUM = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + Constants.APP_DIR_NAME + File.separator + "MainAlbum" + File.separator;
                Constants.PATH_APP_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + Constants.APP_DIR_NAME + File.separator;


                // create readme.txt folder
                InputStream inputStream = getAssets().open("readme.txt");
                if (!new File(appDir.getAbsolutePath() + File.separator + "/readme.txt").exists())
                    FileUtils.writeFile(appDir.getAbsolutePath() + File.separator + "/readme.txt", inputStream);

                // create mainalbum folder
                File mainAlbumDir = new File(Constants.PATH_MAINALBUM);
                if (!mainAlbumDir.exists())
                    mainAlbumDir.mkdirs();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get be called from {@link LockActivity}
     * @param passcode passcode from user set at first time use app
     */
    public void initStorageEncrypt(String passcode) {
        // save preference
        getSharedPreferences(Constants.MAIN_PREF, MODE_PRIVATE)
                .edit()
                .putString(Constants.PIN_CODE, passcode)
                .apply();

        String IVX = "abcdefghijklmnop";
        byte[] SALT = "0000111100001111".getBytes();

        EncryptConfiguration configuration = new EncryptConfiguration.Builder()
                .setEncryptContent(IVX, passcode, SALT)
                .build();

        storage.setEncryptConfiguration(configuration);
    }

    public Storage getStorageManager() { return storage; }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}