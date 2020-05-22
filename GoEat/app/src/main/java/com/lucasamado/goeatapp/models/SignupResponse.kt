package com.lucasamado.goeatapp.models

data class SignupResponse(
    val avatar: String,
    val bar: Any,
    val email: String,
    val id: String,
    val pedidos: List<Any>,
    val roles: String,
    val username: String
)