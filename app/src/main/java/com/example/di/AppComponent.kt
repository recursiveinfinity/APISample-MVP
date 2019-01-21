package com.example.di

import com.example.apidemo_mvp.network.ISSService
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface AppComponent {
    fun issService(): ISSService
}