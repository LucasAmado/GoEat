package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasamado.goeatapp.api.APIError
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.pedido.PedidoDetalleDto
import com.lucasamado.goeatapp.repository.PedidoRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class PedidoDetalleViewModel @Inject constructor(
    var pedidoRepository: PedidoRepository
) : ViewModel() {
    var favBoolean: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    var pedidoSelect: MutableLiveData<Resource<PedidoDetalleDto>> = MutableLiveData()
    var cambioEstado: MutableLiveData<Resource<String>> = MutableLiveData()

    fun getPedido(id: String) = viewModelScope.launch {
        pedidoSelect.value = Resource.Loading()
        val response = pedidoRepository.getPedidoDetalle(id)
        pedidoSelect.value = handleGetPedido(response)
    }

    private fun handleGetPedido(response: Response<PedidoDetalleDto>): Resource<PedidoDetalleDto>? {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = pedidoRepository.parseError(response)
        return Resource.Error(error.status_message)
    }

    fun changePedidoFav(id: String) = viewModelScope.launch { 
        favBoolean.value = Resource.Loading()
        val response = pedidoRepository.changePedidoFavorito(id)
        favBoolean.value = handleChangeFav(response)
    }

    private fun handleChangeFav(response: Response<Boolean>): Resource<Boolean>? {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = pedidoRepository.parseError(response)
        return Resource.Error(error.status_message)
    }

    fun changeEstado(id: String) = viewModelScope.launch {
        cambioEstado.value = Resource.Loading()
        val response = pedidoRepository.cambiarEstado(id)
        cambioEstado.value = handleChangeEstado(response)
    }

    private fun handleChangeEstado(response: Response<String>): Resource<String>? {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = pedidoRepository.parseError(response)
        return Resource.Error(error.status_message)
    }

}