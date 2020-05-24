package com.lucasamado.goeatapp.api

import com.lucasamado.goeatapp.models.bar.BarDto
import com.lucasamado.goeatapp.models.bar.Plato
import com.lucasamado.goeatapp.models.user.LoginRequest
import com.lucasamado.goeatapp.models.user.LoginResponse
import com.lucasamado.goeatapp.models.user.SignupRequest
import com.lucasamado.goeatapp.models.user.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GoEatService {

    //usuarios

    @POST("/signup")
    fun createUser(@Body newUser: SignupRequest): Call<SignupResponse>

    @POST("auth/login")
    fun doLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>

    //Bares

    @GET("bares/")
    fun getBaresList(): Call<List<BarDto>>

    @GET("bares/{id}")
    fun getBarById(@Path("id") id: String): Call<BarDto>

    //Platos

    @GET("platos/tipos/{id}")
    fun getTiposPlatosByBar(@Path("id") id: String): Call<List<String>>

    @GET("platos/{tipo}/bar/{id}")
    fun getPlatosByBarAndTipo(@Path("tipo") tipo: String, @Path("id") id: String): Call<List<Plato>>

    @GET("platos/{id}")
    fun getPlato(@Path("id") id: String): Call<Plato>
}