package com.example.ui.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.apidemo_mvp.BASE_URL_ISS
import com.example.apidemo_mvp.R
import com.example.apidemo_mvp.model.Response
import com.example.apidemo_mvp.network.ISSService
import kotlinx.android.synthetic.main.activity_iss.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), HomeContract.View {

    private val issAdapter = ISSAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iss)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()


        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL_ISS)
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit = retrofitBuilder
            .client(okHttpClient)
            .build()

        val issService = retrofit.create(ISSService::class.java)

        val homePresenter: HomeContract.Presenter = HomePresenter(issService, this)

        rvPasses.layoutManager = LinearLayoutManager(this)
        rvPasses.adapter = issAdapter

        btnSubmit.setOnClickListener {
            homePresenter.getISSPasses(etLatitude.text.toString(),
                etLongitude.text.toString())
        }


    }

    override fun showResults(results: List<Response>) {
        issAdapter.setData(results)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


}
