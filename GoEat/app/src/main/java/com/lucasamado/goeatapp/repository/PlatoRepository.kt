package com.lucasamado.goeatapp.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.bar.Plato
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PlatoRepository  @Inject constructor(var goEatService: GoEatService) {

    var tiposList: MutableLiveData<List<String>> = MutableLiveData()
    var platoList: MutableLiveData<List<Plato>> = MutableLiveData()
    var plato: MutableLiveData<Plato> = MutableLiveData()

    fun findTiposByBar(id: String): MutableLiveData<List<String>> {
        val call: Call<List<String>>? = goEatService.getTiposPlatosByBar(id)
        call?.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    tiposList.value = response.body()
                } else {
                    Toast.makeText(MyApp.instance, "Error al cargar los tipos de platos", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_LONG).show()
            }
        })

        return tiposList
    }

    fun getPlatosByTipo(tipo: String, id: String): MutableLiveData<List<Plato>> {
        val call: Call<List<Plato>>? = goEatService.getPlatosByBarAndTipo(tipo, id)
        call?.enqueue(object : Callback<List<Plato>> {
            override fun onResponse(call: Call<List<Plato>>, response: Response<List<Plato>>) {
                if (response.isSuccessful) {
                    platoList.value = response.body()
                } else {
                    Toast.makeText(MyApp.instance, "Error al cargar los tipos de platos", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<Plato>>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_LONG).show()
            }
        })

        return platoList
    }

    fun getPlato(id: String): MutableLiveData<Plato>{
        val call: Call<Plato> = goEatService.getPlato(id)
        call.enqueue(object : Callback<Plato>{
            override fun onResponse(call: Call<Plato>, response: Response<Plato>) {
                if(response.isSuccessful){
                    plato.value = response.body()
                }else{
                    Toast.makeText(MyApp.instance, "Error al cargar el plato", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Plato>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_LONG).show()
            }
        })
        return plato
    }

}