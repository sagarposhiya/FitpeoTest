package com.example.fitpeoassignment

import android.app.Activity
import android.app.Application
import com.example.fitpeoassignment.di.AppComponent
import com.example.fitpeoassignment.di.DaggerAppComponent
import com.example.fitpeoassignment.utils.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App :  Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun onCreate() {
        super.onCreate()

        // added dagger
        DaggerAppComponent.builder()
            .networkModule(NetworkModule(this))
            .build()
            .inject(this)
    }
}