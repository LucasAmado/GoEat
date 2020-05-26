package com.salesianostriana.dam.apigoeat.models

import com.fasterxml.jackson.annotation.JsonBackReference
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class LineaPedido(

        var cantidad: Int,

        var totalLinea: Double,

        @JsonBackReference
        @ManyToOne
        var plato: Plato? = null,

        @JsonBackReference
        @ManyToOne
        var pedido: Pedido? = null,

        @Id @GeneratedValue
        val id : UUID? = null
) {
    fun calcularPrecioLinea(): Double {
        return plato?.precioU!!*cantidad
    }
}