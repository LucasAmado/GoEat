package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasamado.goeatapp.api.APIError
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.bar.BarDto
import com.lucasamado.goeatapp.repository.BarRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class BarViewModel @Inject constructor(
    var barRepository: BarRepository
) : ViewModel() {

    var listaBares: MutableLiveData<Resource<List<BarDto>>> = MutableLiveData()
    var miBar: MutableLiveData<Resource<BarDto>> = MutableLiveData()
    var tiposComida: MutableLiveData<Resource<List<String>>> = MutableLiveData()

    /**
     * Crgar la lista de bares
     */
    fun getBares() = viewModelScope.launch {
        listaBares.value = Resource.Loading()
        delay(1500)
        val response = barRepository.getBaresList()
        listaBares.value = handleGetListaBares(response)
    }

    private fun handleGetListaBares(response: Response<List<BarDto>>): Resource<List<BarDto>>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    /**
     * Cargar mi bar
     */
    fun getMyBar() = viewModelScope.launch {
        miBar.value = Resource.Loading()
        delay(1500)
        val response = barRepository.getMiBar()
        miBar.value = handleGetMyBar(response)
    }

    private fun handleGetMyBar(response: Response<BarDto>): Resource<BarDto>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = barRepository.parseError(response)
        return Resource.Error(error.status_message)
    }

    fun getTiposComida() = viewModelScope.launch {
        tiposComida.value = Resource.Loading()
        val response = barRepository.getTiposComida()
        tiposComida.value = handleLoadTiposComida(response)
    }

    private fun handleLoadTiposComida(response: Response<List<String>>): Resource<List<String>>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = barRepository.parseError(response)
        return Resource.Error(error.status_message)
    }

    fun getBaresByTipo(tipo: String) = viewModelScope.launch {
        listaBares.value = Resource.Loading()
        val response = barRepository.getBaresByTipoComida(tipo)
        listaBares.value = handleGetListaBares(response)
    }
}