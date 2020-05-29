package com.lucasamado.goeatapp.models.pedido

import com.lucasamado.goeatapp.models.bar.BarDto
import com.lucasamado.goeatapp.models.user.User

data class PedidoDetalleDto(
    val bar: BarDto,
    val comentario: String,
    val favorito: Boolean,
    val fechaPedido: String,
    val horaRecogida: String,
    val id: String,
    val totalPedido: Double,
    val user: User
)