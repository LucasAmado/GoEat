package com.lucasamado.goeatapp.models.user

data class LoginResponse(
    val token: String,
    val user: UserDto
)