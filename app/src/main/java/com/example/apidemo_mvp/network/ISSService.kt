package com.example.apidemo_mvp.network

import com.example.apidemo_mvp.ISS_ENDPOINT
import com.example.apidemo_mvp.model.ISSResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ISSService {

    @GET(ISS_ENDPOINT)
    fun getISSPasses(@Query("lat") latitude: Double,
                     @Query("lon") longitude: Double): Observable<ISSResponse>
}