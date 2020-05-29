package com.lucasamado.goeatapp.models.pedido

import com.lucasamado.goeatapp.models.lineasPedido.LineaPedido
import com.lucasamado.goeatapp.models.user.UserDto

data class Pedido(
    val favorito: Boolean,
    val fechaPedido: String,
    val horaRecogida: String,
    val id: String,
    val lineasPedido: List<LineaPedido>,
    val totalPedido: Double,
    val user: UserDto
)

