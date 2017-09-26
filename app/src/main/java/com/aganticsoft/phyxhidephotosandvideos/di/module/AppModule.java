package com.aganticsoft.phyxhidephotosandvideos.di.module;

import android.app.Application;

import com.aganticsoft.phyxhidephotosandvideos.util.PrefManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ttson
 * Date: 6/16/2017.
 */
@Module(includes = {ViewModelModule.class})
public class AppModule {

    @Provides
    public PrefManager providePrefManager(Application app) {
        return PrefManager.getInstance(app);
    }
}