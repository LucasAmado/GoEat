package com.lucasamado.goeatapp.models.user

data class User(
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val authorities: List<Authority>,
    val avatar: String,
    val credentialsNonExpired: Boolean,
    val email: String,
    val enabled: Boolean,
    val id: String,
    val password: String,
    val fechaCreacion: String,
    val fechaCambio: String,
    val nombreCompleto: String,
    val roles: List<String>,
    val nickName: String
)