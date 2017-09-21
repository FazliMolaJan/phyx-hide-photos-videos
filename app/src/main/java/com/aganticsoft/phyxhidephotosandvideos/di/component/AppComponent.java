package com.aganticsoft.phyxhidephotosandvideos.di.component

import android.app.Application
import com.aganticsoft.phyxhidephotosandvideos.PhyxApp
import com.aganticsoft.phyxhidephotosandvideos.di.module.ActivityBindingModule
import com.aganticsoft.phyxhidephotosandvideos.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by ttson
 * Date: 6/16/2017.
 */
@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class
))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application) : AppComponent.Builder
        fun build() : AppComponent
    }

    fun inject(phyxApp: PhyxApp)
}