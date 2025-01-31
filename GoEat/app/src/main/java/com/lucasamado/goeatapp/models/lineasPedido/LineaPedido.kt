package com.lucasamado.goeatapp.models.lineasPedido

import com.lucasamado.goeatapp.models.plato.Plato

data class LineaPedido(
    val cantidad: Int,
    val id: String,
    val plato: Plato,
    val totalLinea: Double
)
