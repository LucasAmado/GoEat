package com.lucasamado.goeatapp.models.user

data class LoginRequest(
    val password: String,
    val username: String
)