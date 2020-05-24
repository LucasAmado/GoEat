package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.models.user.LoginRequest
import com.lucasamado.goeatapp.models.user.LoginResponse
import com.lucasamado.goeatapp.repository.UserRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    userRepository: UserRepository
): ViewModel() {
    val repo = userRepository

    fun doLogin(loginRequest: LoginRequest): LiveData<LoginResponse> {
        return repo.doLogin(loginRequest)
    }
}