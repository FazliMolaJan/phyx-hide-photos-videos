package com.aganticsoft.phyxhidephotosandvideos.di.module;

import com.aganticsoft.phyxhidephotosandvideos.view.fragment.AlbumFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by ttson
 * Date: 9/24/2017.
 */

@Module
public abstract class MainFragmentModule {
    @ContributesAndroidInjector
    abstract AlbumFragment contributeRepoFragment();
}
