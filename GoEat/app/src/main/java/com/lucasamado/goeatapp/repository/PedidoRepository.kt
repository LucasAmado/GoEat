package com.lucasamado.goeatapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.lucasamado.goeatapp.api.APIError
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.lineasPedido.LineaPedidoDetalle
import com.lucasamado.goeatapp.models.pedido.CreatePedido
import com.lucasamado.goeatapp.models.lineasPedido.LineaPedidoDto
import com.lucasamado.goeatapp.models.pedido.PedidoDetalleDto
import com.lucasamado.goeatapp.models.pedido.PedidoDto
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PedidoRepository @Inject constructor(var goEatService: GoEatService) {

    suspend fun actualizarCarrito(cantidad: Int, id: String) = goEatService.actualizarCarrito(cantidad, id)

    suspend fun deletePlato(id: String) = goEatService.borrarPlato(id)

    suspend fun calcularTotalCarrito() = goEatService.calcularPrcioTotal()

    suspend fun verCarrito() = goEatService.getCarrito()

    suspend fun pagar(createPedido: CreatePedido) = goEatService.pagar(createPedido)

    suspend fun loadMisPedidos() = goEatService.misPedidos()

    suspend fun getLineasPedido(id: String) = goEatService.lineasPedidoByPedidoId(id)

    suspend fun getPedidoDetalle(id: String) = goEatService.getPedidoDetalle(id)

    suspend fun changePedidoFavorito(id: String) = goEatService.editPedidoBoolean(id)

    fun parseError(response: Response<*>): APIError {
        val jsonObject = JSONObject(response.errorBody()!!.string())
        return APIError(jsonObject.getInt("status_code"), jsonObject.getString("status_message"))
    }
}