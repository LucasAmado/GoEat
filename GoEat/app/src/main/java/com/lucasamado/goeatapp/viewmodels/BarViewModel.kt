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

    init {
        getBares()
    }

    fun getBares() = viewModelScope.launch {
        listaBares.value = Resource.Loading()
        delay(1500)
        val response = barRepository.getBaresList()
        listaBares.value = handleGetListaBares(response)
    }

    private fun handleGetListaBares(response: Response<List<BarDto>>): Resource<List<BarDto>>? {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = barRepository.parseError(response)
        return Resource.Error(error.status_message)
    }
}