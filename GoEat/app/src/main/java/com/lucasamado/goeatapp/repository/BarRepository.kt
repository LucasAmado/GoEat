package com.lucasamado.goeatapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.bar.Bar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BarRepository @Inject constructor(var goEatService: GoEatService) {
    var barList: MutableLiveData<List<Bar>> = MutableLiveData()
    var bar: MutableLiveData<Bar> = MutableLiveData()
    var barMapa: MutableLiveData<Bar> = MutableLiveData()

    fun getBaresList(): MutableLiveData<List<Bar>> {
        val call: Call<List<Bar>>? = goEatService.getBaresList()
        call?.enqueue(object : Callback<List<Bar>> {
            override fun onResponse(call: Call<List<Bar>>, response: Response<List<Bar>>) {
                if (response.isSuccessful) {
                    barList.value = response.body()
                } else {
                    Toast.makeText(MyApp.instance, "Error al cargar los bares", Toast.LENGTH_SHORT)
                        .show()
                }

                Log.e("RESPONSE BARES", ""+response.body())
            }

            override fun onFailure(call: Call<List<Bar>>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error. Can't connect to server", Toast.LENGTH_SHORT).show()
            }
        })

        return barList
    }


    fun getBar(id: String): MutableLiveData<Bar>{
        val call: Call<Bar>? = goEatService.getBarById(id)
        call?.enqueue(object : Callback<Bar> {
            override fun onResponse(call: Call<Bar>, response: Response<Bar>) {
                if (response.isSuccessful) {
                    bar.value = response.body()
                } else {
                    Toast.makeText(MyApp.instance, "Error al cargar el bar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Bar>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error. Can't connect to server", Toast.LENGTH_SHORT).show()
            }
        })

        return bar
    }

}