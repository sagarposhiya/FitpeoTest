package com.example.fitpeoassignment.di

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.fitpeoassignment.App
import com.example.fitpeoassignment.utils.NetworkModule
import dagger.Component

@Component(
    modules = [
        ActivitiesModule::class, NetworkModule::class
    ]
)

interface AppComponent {

    fun inject(myApplication: App)
    fun inject (networkModule: NetworkModule)

}