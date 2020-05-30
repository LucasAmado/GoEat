package com.lucasamado.goeatapp.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.user.LoginRequest
import com.lucasamado.goeatapp.models.user.LoginResponse
import com.lucasamado.goeatapp.models.user.SignupRequest
import com.lucasamado.goeatapp.models.user.SignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(var goEatService: GoEatService) {
    var userLogin: MutableLiveData<LoginResponse> = MutableLiveData()

    suspend fun signupUser(signupRequest: SignupRequest) = goEatService.createUser(signupRequest)

    suspend fun doLogin(loginRequest: LoginRequest) = goEatService.doLogin(loginRequest)
}