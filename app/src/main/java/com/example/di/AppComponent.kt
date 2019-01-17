package com.example.di

import com.example.ui.home.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}