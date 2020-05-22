package com.lucasamado.goeatapp.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.common.MyApp
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

    fun signupUser(createUser: SignupRequest): MutableLiveData<SignupResponse>{
        val call: Call<SignupResponse>? = goEatService.createUser(createUser)
        call?.enqueue(object : Callback<SignupResponse> {
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if(response.isSuccessful) {
                    usuarioNuevo.value = response.body()
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

    /*fun parseError(response: Response<*>): APIError {
        val jsonObject = JSONObject(response.errorBody()!!.string())
        return APIError(jsonObject.getInt("status_code"), jsonObject.getString("status_message"))
    }*/
}