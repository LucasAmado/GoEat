package com.lucasamado.goeatapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.lucasamado.goeatapp.api.APIError
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.bar.BarDetailDto
import com.lucasamado.goeatapp.models.bar.BarDto
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BarRepository @Inject constructor(var goEatService: GoEatService) {
    var bar: MutableLiveData<BarDetailDto> = MutableLiveData()
    var barMapa: MutableLiveData<BarDetailDto> = MutableLiveData()
    var horasRecogida: MutableLiveData<List<String>> = MutableLiveData()

    suspend fun getBaresList() = goEatService.getBaresList()

    suspend fun getBar(id: String) = goEatService.getBarById(id)

    suspend fun consultarHorasRecogida(id: String) = goEatService.consultarHorariosRecogidaBar(id)

    fun parseError(response: Response<*>): APIError {
        val jsonObject = JSONObject(response.errorBody()!!.string())
        return APIError(jsonObject.getInt("status_code"), jsonObject.getString("status_message"))
    }
}