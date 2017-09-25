package com.aganticsoft.phyxhidephotosandvideos.di.module;


import com.aganticsoft.phyxhidephotosandvideos.view.fragment.ChooseAlbumFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by ttson
 * Date: 9/25/2017.
 */

@Module
public abstract class MediaChooseFragmentModule {
    @ContributesAndroidInjector
    abstract ChooseAlbumFragment contributeAlbumFragment();
}
