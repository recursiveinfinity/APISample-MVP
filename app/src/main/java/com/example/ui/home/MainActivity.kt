package com.example.ui.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.App
import com.example.apidemo_mvp.BASE_URL_ISS
import com.example.apidemo_mvp.R
import com.example.apidemo_mvp.model.Response
import com.example.apidemo_mvp.network.ISSService
import com.example.di.DaggerAppComponent
import kotlinx.android.synthetic.main.activity_iss.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HomeContract.View {

    private val issAdapter = ISSAdapter()

    @Inject
    lateinit var issService: ISSService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iss)

        (application as App).getComponent().inject(this)

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
