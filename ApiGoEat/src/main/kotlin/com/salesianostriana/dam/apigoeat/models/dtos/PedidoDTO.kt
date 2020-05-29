package com.salesianostriana.dam.apigoeat.models.dtos

import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class CreatePedidoDTO(
        var horaRecogida: LocalTime,
        var comentario: String
)

data class PedidoDTO(
        var fechaPedido: LocalDate,
        var totalPedido: Double,
        var favorito: Boolean = false,
        var horaRecogida: LocalTime? = null,
        var user: User? = null,
        var bar: BarDTO? = null,
        var comentario: String? = null,
        var lineasPedido: List<LineaPedidoPagoDTO> = ArrayList(),
        val id: UUID? = null
)

fun Pedido.toPedidoDTO() = PedidoDTO(fechaPedido, totalPedido, favorito, horaRecogida, user, bar?.toBarDTO(), comentario, lineasPedido!!.map { it.toLineaPedidoPagoDto() }, id)