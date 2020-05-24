package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.models.user.SignupRequest
import com.lucasamado.goeatapp.models.user.SignupResponse
import com.lucasamado.goeatapp.repository.UserRepository
import javax.inject.Inject

class SignupViewModel @Inject constructor(
    userRepository: UserRepository
): ViewModel() {
    val repo = userRepository

    fun createUser(createUser: SignupRequest): LiveData<SignupResponse> {
        return repo.signupUser(createUser)
    }
}