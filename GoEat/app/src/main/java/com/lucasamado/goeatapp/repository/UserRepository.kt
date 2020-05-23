package com.lucasamado.goeatapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.LoginRequest
import com.lucasamado.goeatapp.models.LoginResponse
import com.lucasamado.goeatapp.models.SignupRequest
import com.lucasamado.goeatapp.models.SignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(var goEatService: GoEatService) {
    var usuarioNuevo: MutableLiveData<SignupResponse> = MutableLiveData()
    var userLogin: MutableLiveData<LoginResponse> = MutableLiveData()

    fun signupUser(signupRequest: SignupRequest): MutableLiveData<SignupResponse>{
        val call: Call<SignupResponse>? = goEatService.createUser(signupRequest)
        call?.enqueue(object : Callback<SignupResponse> {
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if(response.isSuccessful) {
                    usuarioNuevo.value = response.body()
                    Toast.makeText(MyApp.instance, "Usuario creado correctamente", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(MyApp.instance, "Error al crear el usuario", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error. Can't connect to server", Toast.LENGTH_SHORT).show()
            }
        })

        return usuarioNuevo

    }

    fun doLogin(loginRequest: LoginRequest): MutableLiveData<LoginResponse>{
        val call: Call<LoginResponse> = goEatService.doLogin(loginRequest)
        call.enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful) {
                    userLogin.value = response.body()
                }else{
                    Toast.makeText(MyApp.instance, "Error al hacer el login", Toast.LENGTH_SHORT).show()
                    Log.e("ERRROR", ""+response.errorBody())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error. Can't connect to server", Toast.LENGTH_SHORT).show()
            }
        })

        return userLogin
    }
}