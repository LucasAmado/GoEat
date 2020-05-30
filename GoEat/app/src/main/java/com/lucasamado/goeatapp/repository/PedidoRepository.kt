package com.lucasamado.goeatapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.lineasPedido.LineaPedidoDetalle
import com.lucasamado.goeatapp.models.pedido.CreatePedido
import com.lucasamado.goeatapp.models.lineasPedido.LineaPedidoDto
import com.lucasamado.goeatapp.models.pedido.PedidoDetalleDto
import com.lucasamado.goeatapp.models.pedido.PedidoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PedidoRepository @Inject constructor(var goEatService: GoEatService) {

    var boolean: MutableLiveData<Boolean> = MutableLiveData()
    var carrito: MutableLiveData<List<LineaPedidoDto>> = MutableLiveData()
    var pedidosDtoList: MutableLiveData<List<PedidoDto>> = MutableLiveData()

    suspend fun actualizarCarrito(cantidad: Int, id: String) = goEatService.actualizarCarrito(cantidad, id)

    fun deletePlato(id: String): MutableLiveData<Boolean>{
        val call: Call<Boolean> = goEatService.borrarPlato(id)
        call.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    boolean.value = response.body()
                    Toast.makeText(MyApp.instance, "Borrado correctamente", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(MyApp.instance, "Error al borrar el plato", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error borrando el plato ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
        return boolean
    }

    suspend fun calcularTotalCarrito() = goEatService.calcularPrcioTotal()

    suspend fun verCarrito() = goEatService.getCarrito()

    suspend fun pagar(createPedido: CreatePedido) = goEatService.pagar(createPedido)

    suspend fun loadMisPedidos() = goEatService.misPedidos()

    suspend fun getLineasPedido(id: String) = goEatService.lineasPedidoByPedidoId(id)

    suspend fun getPedidoDetalle(id: String) = goEatService.getPedidoDetalle(id)

    suspend fun changePedidoFavorito(id: String) = goEatService.editPedidoBoolean(id)
}