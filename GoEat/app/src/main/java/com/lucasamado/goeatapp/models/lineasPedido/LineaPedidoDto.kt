package com.lucasamado.goeatapp.models.lineasPedido

import com.lucasamado.goeatapp.models.pedido.Pedido
import com.lucasamado.goeatapp.models.plato.PlatoDto

data class LineaPedidoDto(
    val cantidad: Int,
    val id: String,
    val pedido: Pedido,
    val plato: PlatoDto,
    val totalLinea: Double
)