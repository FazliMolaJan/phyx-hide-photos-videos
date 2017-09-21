package com.aganticsoft.phyxhidephotosandvideos.di.component;

import android.app.Application;
import com.aganticsoft.phyxhidephotosandvideos.PhyxApp;
import com.aganticsoft.phyxhidephotosandvideos.di.module.ActivityBindingModule;
import com.aganticsoft.phyxhidephotosandvideos.di.module.AppModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;

/**
 * Created by ttson
 * Date: 6/16/2017.
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBindingModule.class
        })
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance AppComponent.Builder application(Application application);
        AppComponent build();
    }

    void inject(PhyxApp phyxApp);
}