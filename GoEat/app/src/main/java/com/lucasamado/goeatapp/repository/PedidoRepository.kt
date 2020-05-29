package com.lucasamado.goeatapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.lucasamado.goeatapp.api.GoEatService
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.pedido.LineaPedidoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PedidoRepository @Inject constructor(var goEatService: GoEatService) {

    var lineaPedidoDto: MutableLiveData<LineaPedidoDto> = MutableLiveData()
    var delete: MutableLiveData<Boolean> = MutableLiveData()
    var total: MutableLiveData<Double> = MutableLiveData()
    var carrito: MutableLiveData<List<LineaPedidoDto>> = MutableLiveData()

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
        val call: Call<Double> = goEatService.consultarTamanyoCarrito()
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
}