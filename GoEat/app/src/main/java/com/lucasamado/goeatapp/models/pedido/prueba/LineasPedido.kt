package com.lucasamado.goeatapp.models.pedido.prueba

data class LineasPedido(
    val cantidad: Int,
    val id: String,
    val plato: Plato,
    val totalLinea: Double
)