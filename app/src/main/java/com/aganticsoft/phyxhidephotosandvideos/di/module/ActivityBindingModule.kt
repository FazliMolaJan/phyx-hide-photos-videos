package com.aganticsoft.phyxhidephotosandvideos.di.module;

import com.aganticsoft.phyxhidephotosandvideos.MainActivity
import com.aganticsoft.phyxhidephotosandvideos.di.scope.ActivityScope;

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by ttson
 * Date: 6/18/2017.
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf())
    abstract fun contributeMainActivty() : MainActivity
}