package com.aganticsoft.phyxhidephotosandvideos.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.aganticsoft.phyxhidephotosandvideos.di.ViewModelKey;
import com.aganticsoft.phyxhidephotosandvideos.viewmodel.MediaViewModel;
import com.aganticsoft.phyxhidephotosandvideos.viewmodel.PhyxViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MediaViewModel.class)
    abstract ViewModel bindUserViewModel(MediaViewModel mediaViewModel);


    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(PhyxViewModelFactory factory);
}
