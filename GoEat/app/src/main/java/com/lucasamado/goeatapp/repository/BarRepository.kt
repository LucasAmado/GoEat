package com.lucasamado.goeatapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.bar.BarDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BarRepository @Inject constructor(var goEatService: GoEatService) {
    var barList: MutableLiveData<List<BarDto>> = MutableLiveData()
    var bar: MutableLiveData<BarDto> = MutableLiveData()
    var barMapa: MutableLiveData<BarDto> = MutableLiveData()

    fun getBaresList(): MutableLiveData<List<BarDto>> {
        val call: Call<List<BarDto>>? = goEatService.getBaresList()
        call?.enqueue(object : Callback<List<BarDto>> {
            override fun onResponse(call: Call<List<BarDto>>, response: Response<List<BarDto>>) {
                if (response.isSuccessful) {
                    barList.value = response.body()
                } else {
                    Toast.makeText(MyApp.instance, "Error al cargar los bares", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<BarDto>>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error. Can't connect to server", Toast.LENGTH_SHORT).show()
            }
        })

        return barList
    }


    fun getBar(id: String): MutableLiveData<BarDto>{
        val call: Call<BarDto>? = goEatService.getBarById(id)
        call?.enqueue(object : Callback<BarDto> {
            override fun onResponse(call: Call<BarDto>, response: Response<BarDto>) {
                if (response.isSuccessful) {
                    bar.value = response.body()
                } else {
                    Toast.makeText(MyApp.instance, "Error al cargar el bar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BarDto>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error. Can't connect to server", Toast.LENGTH_SHORT).show()
            }
        })

        return bar
    }

}