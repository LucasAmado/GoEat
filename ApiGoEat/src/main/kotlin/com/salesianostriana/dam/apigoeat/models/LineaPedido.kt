package com.salesianostriana.dam.apigoeat.models

import com.fasterxml.jackson.annotation.JsonBackReference
import java.util.*
import javax.persistence.*

@Entity
data class LineaPedido(

        var cantidad: Int,

        var totalLinea: Double,

        @JsonBackReference
        @ManyToOne(fetch = FetchType.LAZY)
        var plato: Plato? = null,

        @ManyToOne
        var pedido: Pedido? = null,

        @Id @GeneratedValue
        val id : UUID? = null
) {
    fun calcularPrecioLinea(): Double {
        return plato?.precioU!!*cantidad
    }
}