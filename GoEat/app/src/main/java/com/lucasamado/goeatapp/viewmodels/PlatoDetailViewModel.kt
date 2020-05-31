package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasamado.goeatapp.api.APIError
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.lineasPedido.LineaPedidoDto
import com.lucasamado.goeatapp.models.plato.PlatoDto
import com.lucasamado.goeatapp.repository.PedidoRepository
import com.lucasamado.goeatapp.repository.PlatoRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class PlatoDetailViewModel @Inject constructor(
    var platoRepository: PlatoRepository,
    var pedidoRepository: PedidoRepository
) : ViewModel() {

    var platoSelect: MutableLiveData<Resource<PlatoDto>> = MutableLiveData()
    var lineasCarrito: MutableLiveData<Resource<LineaPedidoDto>> = MutableLiveData()
    var platoDelete: MutableLiveData<Resource<Boolean>> = MutableLiveData()

    /**
     * Get plato
     */

    fun getPlato(id: String) = viewModelScope.launch {
        platoSelect.value = Resource.Loading()
        val response = platoRepository.getPlato(id)
        platoSelect.value = handleGetPlato(response)
    }

    private fun handleGetPlato(response: Response<PlatoDto>): Resource<PlatoDto>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = platoRepository.parseError(response)
        return Resource.Error(error.status_message)
    }

    /**
     * Actualizar los datos del carrito
     */
    fun actualizarCarrito(cantidad: Int, id: String) = viewModelScope.launch {
        lineasCarrito.value = Resource.Loading()
        val response = pedidoRepository.actualizarCarrito(cantidad, id)
        lineasCarrito.value = handleLoadingCarrito(response)
    }

    private fun handleLoadingCarrito(response: Response<LineaPedidoDto>): Resource<LineaPedidoDto>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = pedidoRepository.parseError(response)
        return Resource.Error(error.status_message)
    }

    fun deletePlato(id: String) = viewModelScope.launch {
        platoDelete.value = Resource.Loading()
        val response = pedidoRepository.deletePlato(id)
        platoDelete.value = handleDeletePlato(response)
    }

    private fun handleDeletePlato(response: Response<Boolean>): Resource<Boolean>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = pedidoRepository.parseError(response)
        return Resource.Error(error.status_message)
    }

}