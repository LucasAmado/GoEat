package com.lucasamado.goeatapp.api

import com.lucasamado.goeatapp.models.*
import com.lucasamado.goeatapp.models.bar.Bar
import com.lucasamado.goeatapp.models.bar.BarDetail
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GoEatService {

    @POST("/signup")
    fun createUser(@Body newUser: SignupRequest): Call<SignupResponse>

    @POST("auth/login")
    fun doLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("bares/")
    fun getBaresList(): Call<List<Bar>>

    @GET("bares/{id}")
    fun getBarById(@Path("id") id: String): Call<BarDetail>

    @GET("platos/bar")
    fun getTiposPlatosByBar(@Body barDetail: BarDetail): Call<List<String>>
}