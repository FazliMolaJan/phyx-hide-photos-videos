package com.aganticsoft.phyxhidephotosandvideos;

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.aganticsoft.phyxhidephotosandvideos.di.Injectable
import com.aganticsoft.phyxhidephotosandvideos.di.component.DaggerAppComponent
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber

/**
 * Created by soninit
 * Date: 6/18/2017.
 */
class AppInjector private constructor(){
    companion object {
        fun init(app: PhyxApp) {
            DaggerAppComponent.builder().application(app)
                    .build().inject(app)
            Timber.tag(AppInjector::class.java.simpleName)
            Timber.e("AppInjector init")

            app.registerActivityLifecycleCallbacks(object: Application.ActivityLifecycleCallbacks {

                override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                    Timber.e("Activity created: ${activity!!::class.java.simpleName}")

                    handleAcitivityCreated(activity)
                }

                override fun onActivityPaused(activity: Activity?) {

                }

                override fun onActivityResumed(activity: Activity?) {

                }

                override fun onActivityStarted(activity: Activity?) {

                }

                override fun onActivityDestroyed(activity: Activity?) {

                }

                override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

                }

                override fun onActivityStopped(activity: Activity?) {

                }
            })
        }

        fun handleAcitivityCreated(activity: Activity) {
            if (activity is HasSupportFragmentInjector) {
                AndroidInjection.inject(activity)
                Timber.e("HasSupportFragmentInjector inject")
            }
            if (activity is FragmentActivity)
                activity.supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentCreated(fm: FragmentManager?, f: Fragment?, savedInstanceState: Bundle?) {
                        Timber.e("Fragment created %s", f!!::class.java.simpleName)

                        if (f is Injectable)
                            AndroidSupportInjection.inject(f)
                    }
                }, true)
        }
    }
}