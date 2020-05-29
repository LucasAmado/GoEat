package com.lucasamado.goeatapp.models.lineasPedido

import com.lucasamado.goeatapp.models.pedido.PedidoDetalleDto
import com.lucasamado.goeatapp.models.plato.PlatoPedidoDto

data class LineaPedidoDetalle(
    val cantidad: Int,
    val id: String,
    val pedido: PedidoDetalleDto,
    val plato: PlatoPedidoDto,
    val totalLinea: Double
)