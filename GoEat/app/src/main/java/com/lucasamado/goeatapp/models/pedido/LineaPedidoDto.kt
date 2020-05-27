package com.lucasamado.goeatapp.models.pedido

import com.lucasamado.goeatapp.models.plato.Plato

data class LineaPedidoDto(
    val cantidad: Int,
    val id: String,
    val pedido: Pedido,
    val plato: Plato,
    val totalLinea: Double
)