package com.lucasamado.goeatapp.repository

import com.lucasamado.goeatapp.api.APIError
import com.lucasamado.goeatapp.api.GoEatUser
import com.lucasamado.goeatapp.models.user.LoginRequest
import com.lucasamado.goeatapp.models.user.SignupRequest
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(var goEatUser: GoEatUser) {

    suspend fun signupUser(signupRequest: SignupRequest) = goEatUser.createUser(signupRequest)

    suspend fun doLogin(loginRequest: LoginRequest) = goEatUser.doLogin(loginRequest)

    fun parseError(response: Response<*>): APIError {
        val jsonObject = JSONObject(response.errorBody()!!.string())
        return APIError(jsonObject.getInt("status_code"), jsonObject.getString("status_message"))
    }
}