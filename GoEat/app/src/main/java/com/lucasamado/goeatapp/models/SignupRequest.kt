package com.lucasamado.goeatapp.models

data class SignupRequest(
    val email: String,
    val password: String,
    val password2: String,
    val username: String
)