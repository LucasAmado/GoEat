package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasamado.goeatapp.api.APIError
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.user.SignupRequest
import com.lucasamado.goeatapp.models.user.SignupResponse
import com.lucasamado.goeatapp.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class SignupViewModel @Inject constructor(
    var userRepository: UserRepository
): ViewModel() {
    
    var usuarioCreado: MutableLiveData<Resource<SignupResponse>> = MutableLiveData()

    fun createUser(createUser: SignupRequest) = viewModelScope.launch { 
        usuarioCreado.value = Resource.Loading()
        val response = userRepository.signupUser(createUser)
        usuarioCreado.value = handleDoSignUp(response)
    }

    private fun handleDoSignUp(response: Response<SignupResponse>): Resource<SignupResponse>? {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = userRepository.parseError(response)
        return Resource.Error(error.status_message)
    }
}