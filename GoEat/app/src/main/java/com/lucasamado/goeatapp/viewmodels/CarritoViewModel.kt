package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.pedido.CreatePedido
import com.lucasamado.goeatapp.models.pedido.PedidoDto
import com.lucasamado.goeatapp.repository.BarRepository
import com.lucasamado.goeatapp.repository.PedidoRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class CarritoViewModel @Inject constructor(
    var pedidoRepository: PedidoRepository,
    var barRepository: BarRepository
) : ViewModel() {

    var total: MutableLiveData<Resource<Double>> = MutableLiveData()
    var horarios: MutableLiveData<Resource<List<String>>> = MutableLiveData()
    var pagar: MutableLiveData<Resource<PedidoDto>> = MutableLiveData()

    /**
     * calcular el total del carrito
     */
    fun totalCarrito() = viewModelScope.launch {
        total.value = Resource.Loading()
        val response = pedidoRepository.calcularTotalCarrito()
        total.value = handleCalcularTota(response)
    }

    private fun handleCalcularTota(response: Response<Double>): Resource<Double>? {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    /**
     * Devuelve las horas disponibles para recoger el pedido
     */
    fun horasRecogida(id: String) = viewModelScope.launch {
        horarios.value = Resource.Loading()
        val response = barRepository.consultarHorasRecogida(id)
        horarios.value = handleConsultarHorarios(response)
    }

    private fun handleConsultarHorarios(response: Response<List<String>>): Resource<List<String>>? {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    /**
     * Pago del carrito
     */
    fun pagarPedido(createPedido: CreatePedido) = viewModelScope.launch {
        pagar.value = Resource.Loading()
        val response = pedidoRepository.pagar(createPedido)
        pagar.value = handlePagar(response)
    }

    private fun handlePagar(response: Response<PedidoDto>): Resource<PedidoDto>? {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}