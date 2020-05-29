package com.lucasamado.goeatapp.models.pedido

data class LineaPedido(
    val cantidad: Int,
    val id: String,
    val plato: Plato,
    val totalLinea: Double
)