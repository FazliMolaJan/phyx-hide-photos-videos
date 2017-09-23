package com.aganticsoft.phyxhidephotosandvideos.di.module;

import com.aganticsoft.phyxhidephotosandvideos.PhyxApp;
import com.aganticsoft.phyxhidephotosandvideos.util.PrefManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ttson
 * Date: 6/16/2017.
 */
@Module
public class AppModule {
    @Singleton
    @Provides
    public PrefManager providePrefManager(PhyxApp app) {
        return PrefManager.getInstance(app);
    }
}