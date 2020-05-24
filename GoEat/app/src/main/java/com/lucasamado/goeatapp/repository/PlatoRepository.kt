package com.lucasamado.goeatapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.LoginResponse
import com.lucasamado.goeatapp.models.SignupRequest
import com.lucasamado.goeatapp.models.SignupResponse
import com.lucasamado.goeatapp.models.bar.BarDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PlatoRepository  @Inject constructor(var goEatService: GoEatService) {

}