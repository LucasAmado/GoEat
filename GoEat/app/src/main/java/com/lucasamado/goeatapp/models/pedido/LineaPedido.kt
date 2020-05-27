package com.lucasamado.goeatapp.models.pedido

data class LineaPedido(
    val cantidad: Int,
    val id: String,
    val totalLinea: Double
)