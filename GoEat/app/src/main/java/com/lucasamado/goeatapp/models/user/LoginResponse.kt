package com.lucasamado.goeatapp.models.user

import com.lucasamado.goeatapp.models.user.User

data class LoginResponse(
    val token: String,
    val user: User
)