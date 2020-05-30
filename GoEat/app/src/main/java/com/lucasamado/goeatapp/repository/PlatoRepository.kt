package com.lucasamado.goeatapp.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.plato.Plato
import com.lucasamado.goeatapp.models.plato.PlatoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PlatoRepository  @Inject constructor(var goEatService: GoEatService) {

    suspend fun findTiposByBar(id: String) = goEatService.getTiposPlatosByBar(id)

    suspend fun getPlatosByTipo(tipo: String, id: String) = goEatService.getPlatosByBarAndTipo(tipo, id)

    suspend fun getPlato(id: String) = goEatService.getPlato(id)
}