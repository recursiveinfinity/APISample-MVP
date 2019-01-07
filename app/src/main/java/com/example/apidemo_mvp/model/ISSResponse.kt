package com.example.apidemo_mvp.model

data class ISSResponse(
    val message: String, val request: Request,
    val response: List<Response>
)