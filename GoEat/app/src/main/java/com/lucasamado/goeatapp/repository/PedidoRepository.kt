package com.lucasamado.goeatapp.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.pedido.CreatePedido
import com.lucasamado.goeatapp.models.pedido.LineaPedidoDto
import com.lucasamado.goeatapp.models.pedido.PedidoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PedidoRepository @Inject constructor(var goEatService: GoEatService) {

    var lineaPedidoDto: MutableLiveData<LineaPedidoDto> = MutableLiveData()
    var delete: MutableLiveData<Boolean> = MutableLiveData()
    var total: MutableLiveData<Double> = MutableLiveData()
    var carrito: MutableLiveData<List<LineaPedidoDto>> = MutableLiveData()
    var pedidoDto: MutableLiveData<PedidoDto> = MutableLiveData()
    var pedidosDtoList: MutableLiveData<List<PedidoDto>> = MutableLiveData()

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
                    delete.value = response.body()
                    Toast.makeText(MyApp.instance, "Borrado correctamente", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(MyApp.instance, "Error al borrar el plato", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error borrando el plato ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
        return delete
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
}