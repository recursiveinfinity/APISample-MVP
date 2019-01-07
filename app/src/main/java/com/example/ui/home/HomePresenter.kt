package com.example.ui.home

import com.example.apidemo_mvp.model.ISSResponse
import com.example.apidemo_mvp.network.ISSService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(
    private val issService: ISSService,
    private val view: HomeContract.View) : HomeContract.Presenter {


    override fun getISSPasses(latitude: String, longitude: String) {
        issService.getISSPasses(latitude.toFloat(), longitude.toFloat()).enqueue(object : Callback<ISSResponse> {
            override fun onFailure(call: Call<ISSResponse>, throwable: Throwable) {
                view.showError("Please try again")
            }

            override fun onResponse(call: Call<ISSResponse>, response: Response<ISSResponse>) {
                if (response.isSuccessful) {
                    view.showResults(response.body()?.response ?: emptyList())
                } else {
                    view.showError("An unknown error occurred")
                }
            }

        })
    }

}