package com.aganticsoft.phyxhidephotosandvideos.di.module;

import com.aganticsoft.phyxhidephotosandvideos.view.activity.MainActivity;
import com.aganticsoft.phyxhidephotosandvideos.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by ttson
 * Date: 6/18/2017.
 */
@Module
public abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {})
    abstract MainActivity contributeMainActivty();
}