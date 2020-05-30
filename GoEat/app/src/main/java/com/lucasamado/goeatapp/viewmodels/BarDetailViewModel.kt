package com.lucasamado.goeatapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.bar.BarDetailDto
import com.lucasamado.goeatapp.repository.BarRepository
import com.lucasamado.goeatapp.repository.PlatoRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class BarDetailViewModel @Inject constructor(
    var barRepository: BarRepository,
    var platoRepository: PlatoRepository
) : ViewModel() {

    var barSelect: MutableLiveData<Resource<BarDetailDto>> = MutableLiveData()
    var tiposPlato: MutableLiveData<Resource<List<String>>> = MutableLiveData()

    fun getDetailBar(id: String) = viewModelScope.launch {
        barSelect.value = Resource.Loading()
        val response = barRepository.getBar(id)
        barSelect.value = handleGetBarResponse(response)
    }

    private fun handleGetBarResponse(response: Response<BarDetailDto>): Resource<BarDetailDto>? {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getTipos(id: String) = viewModelScope.launch {
        tiposPlato.value = Resource.Loading()
        val response = platoRepository.findTiposByBar(id)
        tiposPlato.value = handleGetTiposPlatos(response)
    }

    private fun handleGetTiposPlatos(response: Response<List<String>>): Resource<List<String>>? {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}