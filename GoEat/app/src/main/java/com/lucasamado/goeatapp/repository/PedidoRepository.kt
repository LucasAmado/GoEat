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

    var lineaPedidoDto: MutableLiveData<LineaPedidoDto> = MutableLiveData()
    var boolean: MutableLiveData<Boolean> = MutableLiveData()
    var total: MutableLiveData<Double> = MutableLiveData()
    var carrito: MutableLiveData<List<LineaPedidoDto>> = MutableLiveData()
    var pedidoDto: MutableLiveData<PedidoDto> = MutableLiveData()
    var pedidosDtoList: MutableLiveData<List<PedidoDto>> = MutableLiveData()
    var lineasPedidoDetalle: MutableLiveData<List<LineaPedidoDetalle>> = MutableLiveData()
    var pedidoDetalle: MutableLiveData<PedidoDetalleDto> = MutableLiveData()

    fun actualizarCarrito(cantidad: Int, id: String): MutableLiveData<LineaPedidoDto> {
        val call: Call<LineaPedidoDto> = goEatService.actualizarCarrito(cantidad, id)
        call.enqueue(object : Callback<LineaPedidoDto> {
            override fun onResponse(call: Call<LineaPedidoDto>, response: Response<LineaPedidoDto>) {
                if (response.isSuccessful) {
                    lineaPedidoDto.value = response.body()
                    Toast.makeText(MyApp.instance, "Carrito actualizado", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(MyApp.instance, "Error al actualizar el carrito", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<LineaPedidoDto>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_LONG).show()
            }
        })

        return lineaPedidoDto
    }

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

    fun calcularTotalCarrito(): MutableLiveData<Double>{
        val call: Call<Double> = goEatService.calcularPrcioTotal()
        call.enqueue(object : Callback<Double>{
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if(response.isSuccessful){
                    total.value = response.body()
                }else{
                    Toast.makeText(MyApp.instance, "Error al cargar el total del carrito", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_LONG).show()
            }
        })

        return total
    }

    fun verCarrito(): MutableLiveData<List<LineaPedidoDto>>{
        val call: Call<List<LineaPedidoDto>> = goEatService.getCarrito()
        call.enqueue(object : Callback<List<LineaPedidoDto>>{
            override fun onResponse(
                call: Call<List<LineaPedidoDto>>,
                response: Response<List<LineaPedidoDto>>
            ) {
               if(response.isSuccessful){
                   carrito.value = response.body()
               }else{
                   Toast.makeText(MyApp.instance, "Error al cargar el carrito", Toast.LENGTH_LONG).show()
               }
            }

            override fun onFailure(call: Call<List<LineaPedidoDto>>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error en el carrito: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
        return carrito
    }

    fun pagar(createPedido: CreatePedido): MutableLiveData<PedidoDto>{
        val call: Call<PedidoDto> = goEatService.pagar(createPedido)
        call.enqueue(object : Callback<PedidoDto>{
            override fun onResponse(call: Call<PedidoDto>, response: Response<PedidoDto>) {
                if(response.isSuccessful){
                    pedidoDto.value = response.body()
                    Toast.makeText(MyApp.instance, "Pago realizado con éxito", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(MyApp.instance, "Error al pagar", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<PedidoDto>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error pagando: $t.message", Toast.LENGTH_LONG).show()
            }
        })
        return pedidoDto
    }

    fun loadMisPedidos(): MutableLiveData<List<PedidoDto>>{
        val call: Call<List<PedidoDto>> = goEatService.misPedidos()
        call.enqueue(object : Callback<List<PedidoDto>>{
            override fun onResponse(
                call: Call<List<PedidoDto>>,
                response: Response<List<PedidoDto>>
            ) {
                if(response.isSuccessful){
                    pedidosDtoList.value = response.body()
                }else{
                    Toast.makeText(MyApp.instance, "Error al cargar mis pedidos", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<PedidoDto>>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error en el carrito: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })

        return pedidosDtoList
    }

    fun getLineasPedido(id: String): MutableLiveData<List<LineaPedidoDetalle>> {
        val call: Call<List<LineaPedidoDetalle>> = goEatService.lineasPedidoByPedidoId(id)
        call.enqueue(object : Callback<List<LineaPedidoDetalle>>{
            override fun onResponse(
                call: Call<List<LineaPedidoDetalle>>,
                response: Response<List<LineaPedidoDetalle>>
            ) {
                if(response.isSuccessful){
                    lineasPedidoDetalle.value = response.body()
                }else{
                    Toast.makeText(MyApp.instance, "Error al cargar las lineas de pedido", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<LineaPedidoDetalle>>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error en el carrito: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
        return lineasPedidoDetalle
    }

    fun getPedidoDetalle(id: String): MutableLiveData<PedidoDetalleDto>{
        val call: Call<PedidoDetalleDto> = goEatService.getPedidoDetalle(id)
        call.enqueue(object : Callback<PedidoDetalleDto>{
            override fun onResponse(call: Call<PedidoDetalleDto>, response: Response<PedidoDetalleDto>) {
                if(response.isSuccessful){
                    pedidoDetalle.value = response.body()
                }else{
                    Toast.makeText(MyApp.instance, "Error al pagar", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<PedidoDetalleDto>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error en el detalle: $t.message", Toast.LENGTH_LONG).show()
            }
        })
        return pedidoDetalle
    }

    fun changePedidoFavorito(id: String): MutableLiveData<Boolean>{
        val call: Call<Boolean> = goEatService.editPedidoBoolean(id)
        call.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    boolean.value = response.body()
                }else{
                    Toast.makeText(MyApp.instance, "No se ha podido cambiar el pedido", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error modificando el pedido ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
        return boolean
    }
}