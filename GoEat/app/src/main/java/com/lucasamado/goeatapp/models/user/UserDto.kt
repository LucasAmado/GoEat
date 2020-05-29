package com.lucasamado.goeatapp.models.user

import com.lucasamado.goeatapp.models.bar.Bar

data class UserDto(
    val avatar: String,
    val bar: Bar,
    val email: String,
    val id: String,
    val pedidos: List<Any>,
    val roles: String,
    val username: String
)