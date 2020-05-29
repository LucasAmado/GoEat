package com.lucasamado.goeatapp.models.pedido

import com.lucasamado.goeatapp.models.bar.BarDto
import com.lucasamado.goeatapp.models.user.User

data class PedidoDto(
    val bar: BarDto,
    val favorito: Boolean,
    val fechaPedido: String,
    val horaRecogida: String,
    val id: String,
    val lineasPedido: List<LineaPedido>,
    val totalPedido: Double,
    val user: User
)