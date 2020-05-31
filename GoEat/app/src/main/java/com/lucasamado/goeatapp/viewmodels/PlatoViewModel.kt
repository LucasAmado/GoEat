package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasamado.goeatapp.api.APIError
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.plato.Plato
import com.lucasamado.goeatapp.repository.PlatoRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class PlatoViewModel @Inject constructor(
    var platoRepository: PlatoRepository
) : ViewModel() {

    var platosList: MutableLiveData<Resource<List<Plato>>> = MutableLiveData()

    fun getListaPlatosByTipo(tipo: String, id: String) = viewModelScope.launch {
        platosList.value = Resource.Loading()
        val response =  platoRepository.getPlatosByTipo(tipo, id)
        platosList.value = handleGetPlatosByTipo(response)
    }

    private fun handleGetPlatosByTipo(response: Response<List<Plato>>): Resource<List<Plato>>? {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = platoRepository.parseError(response)
        return Resource.Error(error.status_message)
    }
}