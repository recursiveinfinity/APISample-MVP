package com.example.ui.home.di

import com.example.apidemo_mvp.network.ISSService
import com.example.ui.home.HomeContract
import com.example.ui.home.HomePresenter
import dagger.Module
import dagger.Provides

@Module
class HomeModule(private val view: HomeContract.View) {


    @HomeScope
    @Provides
    fun provideHomePresenter(issService: ISSService)
            : HomeContract.Presenter {
        return HomePresenter(issService, view)
    }
}