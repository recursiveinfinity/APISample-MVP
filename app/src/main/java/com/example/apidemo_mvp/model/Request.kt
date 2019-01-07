package com.example.apidemo_mvp.model

data class Request(val altitude: Int, val datetime: Long, val latitude: Float,
                   val longitude: Float, val passes: Int)