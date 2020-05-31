package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasamado.goeatapp.api.APIError
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.user.LoginRequest
import com.lucasamado.goeatapp.models.user.LoginResponse
import com.lucasamado.goeatapp.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    var userRepository: UserRepository
): ViewModel() {
    
    var userLogin: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()

    fun doLogin(loginRequest: LoginRequest) = viewModelScope.launch { 
        userLogin.value = Resource.Loading()
        val response = userRepository.doLogin(loginRequest)
        userLogin.value = handleDoLogin(response)
    }

    private fun handleDoLogin(response: Response<LoginResponse>): Resource<LoginResponse>? {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        val error: APIError = userRepository.parseError(response)
        return Resource.Error(error.status_message)
    }
}