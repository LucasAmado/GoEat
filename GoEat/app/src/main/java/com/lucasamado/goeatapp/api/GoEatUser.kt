package com.lucasamado.goeatapp.api

import com.lucasamado.goeatapp.models.user.LoginRequest
import com.lucasamado.goeatapp.models.user.LoginResponse
import com.lucasamado.goeatapp.models.user.SignupRequest
import com.lucasamado.goeatapp.models.user.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GoEatUser {

    @POST("/signup")
    suspend fun createUser(@Body newUser: SignupRequest): Response<UserDto>

    @POST("auth/login")
    suspend fun doLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>
}