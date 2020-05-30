package com.lucasamado.goeatapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.bar.BarDetailDto
import com.lucasamado.goeatapp.models.bar.BarDto
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

    fun consultarHorasRecogida(id: String): MutableLiveData<List<String>>{
        val call: Call<List<String>> = goEatService.consultarHorariosRecogidaBar(id)
        call.enqueue(object : Callback<List<String>>{
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>
            ) {
                if(response.isSuccessful){
                    horasRecogida.value = response.body()
                }else{
                    Toast.makeText(MyApp.instance, "Error al cargar las horas de recogida", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error en las horas: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })

        return horasRecogida
    }

}