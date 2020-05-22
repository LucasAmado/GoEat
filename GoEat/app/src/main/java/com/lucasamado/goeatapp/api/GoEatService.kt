package com.lucasamado.goeatapp.api

import com.lucasamado.goeatapp.models.SignupRequest
import com.lucasamado.goeatapp.models.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GoEatService {

    @POST("/signup")
    fun createUser(@Body newUser: SignupRequest): Call<SignupResponse>

    /*@POST("auth/login")
    fun doLogin(@Body user: LoginRequest): Call<LoginResponse>*/
}