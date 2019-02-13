package com.example.ui.home

import com.example.apidemo_mvp.network.ISSService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.SocketException

class HomePresenter(
    private val issService: ISSService,
    private val view: HomeContract.View
) : HomeContract.Presenter {
    private val compositeDisposable = CompositeDisposable()


    override fun getISSPasses(latitude: String, longitude: String) {
        compositeDisposable.add(issService.getISSPasses(
            latitude.toDouble(),
            longitude.toDouble()
        )
            .subscribeOn(Schedulers.io())
            .filter { !it.response.isNullOrEmpty() }
            .map { it.response }
            .flatMap { Observable.fromIterable(it) }
         //   .map { com.example.apidemo_mvp.model.Response(100, 100L) }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.showResults(it)
            },
                { failure ->
                    if (failure is SocketException) {
                        view.showError("Socket Error")
                    } else {
                        view.showError(failure?.message ?: "An unknown error happened")
                    }})
        )
    }
}