package com.lucasamado.goeatapp.api

import com.lucasamado.goeatapp.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GoEatService {

    @POST("/signup")
    fun createUser(@Body newUser: SignupRequest): Call<SignupResponse>

    @POST("auth/login")
    fun doLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("bares/")
    fun getBaresList(): Call<List<Bar>>
}