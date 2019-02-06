package com.example.ui.home

import com.example.apidemo_mvp.model.ISSResponse
import com.example.apidemo_mvp.network.ISSService
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(
    private val issService: ISSService,
    private val view: HomeContract.View) : HomeContract.Presenter {
    private val compositeDisposable = CompositeDisposable()


    override fun getISSPasses(latitude: String, longitude: String) {
        compositeDisposable.add(issService.getISSPasses(latitude.toFloat(),
            longitude.toFloat())
            .subscribeOn(Schedulers.io())
            .filter { !it.response.isNullOrEmpty() }
            .map { it.response }
            .flatMap { Observable.fromIterable(it) }
            .map { com.example.apidemo_mvp.model.Response(100, 100L) }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {view.showResults(it) },
                { failure -> view.showError(failure?.message ?: "An unknown error happened" )}))
    }
}