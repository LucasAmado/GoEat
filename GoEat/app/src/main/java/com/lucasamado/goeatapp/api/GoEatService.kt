package com.lucasamado.goeatapp.api

import com.lucasamado.goeatapp.models.*
import com.lucasamado.goeatapp.models.bar.Bar
import com.lucasamado.goeatapp.models.bar.Plato
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
    fun getBarById(@Path("id") id: String): Call<Bar>

    @GET("platos/tipos/{id}")
    fun getTiposPlatosByBar(@Path("id") id: String): Call<List<String>>

    @GET("platos/{tipo}/bar/{id}")
    fun getPlatosByBarAndTipo(@Path("tipo") tipo: String, @Path("id") id: String): Call<List<Plato>>
}