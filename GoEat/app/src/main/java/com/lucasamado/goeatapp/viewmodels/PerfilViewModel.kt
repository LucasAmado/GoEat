package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasamado.goeatapp.api.APIError
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.user.EditarUser
import com.lucasamado.goeatapp.models.user.UserDto
import com.lucasamado.goeatapp.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class PerfilViewModel  @Inject constructor(
    var userRepository: UserRepository
): ViewModel() {

    var me: MutableLiveData<Resource<UserDto>> = MutableLiveData()

    fun getMe() = viewModelScope.launch {
        me.value = Resource.Loading()
        val response = userRepository.findMe()
        me.value = handleMe(response)
    }

    fun editarMiPerfil(editarUser: EditarUser) = viewModelScope.launch {
        me.value = Resource.Loading()
        val response = userRepository.editarMiPerfil(editarUser)
        me.value = handleMe(response)
    }

    private fun handleMe(response: Response<UserDto>): Resource<UserDto>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = userRepository.parseError(response)
        return Resource.Error(error.status_message)
    }
}