package com.example.apidemo_mvp.network

import com.example.apidemo_mvp.ISS_ENDPOINT
import com.example.apidemo_mvp.model.ISSResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ISSService {

    @GET(ISS_ENDPOINT)
    fun getISSPasses(@Query("lat") latitude: Float,
                     @Query("lon") longitude: Float): Call<ISSResponse>
}