package com.lucasamado.goeatapp.models.user

data class SignupRequest(
    val email: String,
    val password: String,
    val password2: String,
    val username: String
)