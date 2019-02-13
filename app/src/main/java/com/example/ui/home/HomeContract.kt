package com.example.ui.home

import com.example.apidemo_mvp.model.Response


interface HomeContract {

    interface View {
        fun showResults(results: List<Response>)
        fun showError(message: String)
        fun showPictures()
    }

    interface Presenter {
        fun getISSPasses(latitude: String, longitude: String)
    }

}