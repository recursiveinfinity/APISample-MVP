package com.example

import android.app.Application
import com.example.di.AppComponent
import com.example.di.DaggerAppComponent

class App : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .build();
    }

    fun getComponent() = appComponent
}