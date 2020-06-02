package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasamado.goeatapp.api.APIError
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.bar.BarDto
import com.lucasamado.goeatapp.models.bar.EditarBar
import com.lucasamado.goeatapp.repository.BarRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class EditarBarViewModel @Inject constructor(
    var barRepository: BarRepository
) : ViewModel() {

    var miBar: MutableLiveData<Resource<BarDto>> = MutableLiveData()

    fun getMyBar() = viewModelScope.launch {
        miBar.value = Resource.Loading()
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

    fun editarBar(editarBar: EditarBar) = viewModelScope.launch {
        miBar.value = Resource.Loading()
        val response = barRepository.editarBar(editarBar)
        miBar.value = handleEditMyBar(response)
    }

    private fun handleEditMyBar(response: Response<BarDto>): Resource<BarDto>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = barRepository.parseError(response)
        return Resource.Error(error.status_message)
    }
}