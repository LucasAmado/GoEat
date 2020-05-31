package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasamado.goeatapp.api.APIError
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.lineasPedido.LineaPedidoDetalle
import com.lucasamado.goeatapp.models.lineasPedido.LineaPedidoDto
import com.lucasamado.goeatapp.repository.PedidoRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class LineaPedidoViewModel @Inject constructor(
    var pedidoRepository: PedidoRepository
) : ViewModel() {

    var carrito: MutableLiveData<Resource<List<LineaPedidoDto>>> = MutableLiveData()
    var lineasPedido: MutableLiveData<Resource<List<LineaPedidoDetalle>>> = MutableLiveData()

    /**
     * Mostrar las lineas de pedido del carrito
     */
    fun verCarrito() = viewModelScope.launch {
        carrito.value = Resource.Loading()
        val response = pedidoRepository.verCarrito()
        carrito.value = handleCargarCarrito(response)
    }

    private fun handleCargarCarrito(response: Response<List<LineaPedidoDto>>): Resource<List<LineaPedidoDto>>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = pedidoRepository.parseError(response)
        return Resource.Error(error.status_message)
    }

    /**
     * Cargar líneas de un pedido
     */

    fun lineasPedidoByIdPedido(id: String) = viewModelScope.launch {
        lineasPedido.value = Resource.Loading()
        val response = pedidoRepository.getLineasPedido(id)
        lineasPedido.value = handleCargarLineas(response)
    }

    private fun handleCargarLineas(response: Response<List<LineaPedidoDetalle>>): Resource<List<LineaPedidoDetalle>>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = pedidoRepository.parseError(response)
        return Resource.Error(error.status_message)
    }
}