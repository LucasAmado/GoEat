package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.pedido.PedidoDto
import com.lucasamado.goeatapp.repository.PedidoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class PedidoViewModel @Inject constructor(
    var pedidoRepository: PedidoRepository
) : ViewModel() {

    var misPedidos: MutableLiveData<Resource<List<PedidoDto>>> = MutableLiveData()


    fun loadMisPedidos() = viewModelScope.launch { 
        misPedidos.value = Resource.Loading()
        delay(1500)
        val response = pedidoRepository.loadMisPedidos()
        misPedidos.value = handleLoadMisPedidos(response)
    }

    private fun handleLoadMisPedidos(response: Response<List<PedidoDto>>): Resource<List<PedidoDto>>? {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}