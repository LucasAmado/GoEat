package com.salesianostriana.dam.apigoeat.models.dtos

import com.salesianostriana.dam.apigoeat.models.LineaPedido
import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.Plato
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

data class LineaPedidoDTO(
        var cantidad: Int,
        var totalLinea: Double,
        var plato: Plato? = null,
        var pedido: Pedido? = null,
        val id : UUID? = null
)

fun LineaPedido.toLineaPedidoDto() = LineaPedidoDTO(cantidad, totalLinea, plato, pedido, id)