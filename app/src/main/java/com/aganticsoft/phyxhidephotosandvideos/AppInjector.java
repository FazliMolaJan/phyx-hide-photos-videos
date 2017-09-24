package com.aganticsoft.phyxhidephotosandvideos;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.aganticsoft.phyxhidephotosandvideos.di.Injectable;
import com.aganticsoft.phyxhidephotosandvideos.di.component.DaggerAppComponent;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

/**
 * Created by soninit
 * Date: 6/18/2017.
 */
public class AppInjector {
    private AppInjector() {}


        public static void init(PhyxApp app) {
            DaggerAppComponent.builder().application(app)
                    .build().inject(app);
            Timber.tag(AppInjector.class.getSimpleName());
            Timber.e("AppInjector init");

            app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

                @Override
                public void onActivityCreated(Activity activity, Bundle bundle) {
                    Timber.e("Activity created: %s", activity.getClass().getName());

                    handleAcitivityCreated(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {

                }
            });
        }

        public static void handleAcitivityCreated(Activity activity) {
            if (activity instanceof HasSupportFragmentInjector || activity instanceof Injectable) {
                AndroidInjection.inject(activity);
                Timber.e("HasSupportFragmentInjector inject");
            }
            if (activity instanceof FragmentActivity)
                ((FragmentActivity) activity).getSupportFragmentManager()
                        .registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                            @Override
                            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                                Timber.e("Fragment created %s", f.getClass().getSimpleName());

                                if (f instanceof Injectable)
                                AndroidSupportInjection.inject(f);
                            }
                        }, true);
        }
}