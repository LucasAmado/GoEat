package com.lucasamado.goeatapp.repository

import com.lucasamado.goeatapp.api.APIError
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.models.bar.EditarBar
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BarRepository @Inject constructor(var goEatService: GoEatService) {

    suspend fun getBaresList() = goEatService.getBaresList()

    suspend fun getBar(id: String) = goEatService.getBarById(id)

    suspend fun consultarHorasRecogida(id: String) = goEatService.consultarHorariosRecogidaBar(id)

    suspend fun getMiBar() = goEatService.getMiBar()

    suspend fun editarBar(editarBar: EditarBar) = goEatService.editarBar(editarBar)

    suspend fun getTiposComida() = goEatService.tiposComida()

    suspend fun getBaresByTipoComida(tipo: String) = goEatService.getBaresByTipoComida(tipo)

    fun parseError(response: Response<*>): APIError {
        val jsonObject = JSONObject(response.errorBody()!!.string())
        return APIError(jsonObject.getInt("status_code"), jsonObject.getString("status_message"))
    }
}