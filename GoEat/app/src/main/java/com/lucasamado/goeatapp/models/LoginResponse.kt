package com.lucasamado.goeatapp.models

data class LoginResponse(
    val token: String,
    val user: User
)