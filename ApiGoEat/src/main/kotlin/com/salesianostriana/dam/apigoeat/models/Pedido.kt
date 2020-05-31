package com.salesianostriana.dam.apigoeat.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.persistence.*

enum class Estado {
    SOLICITADO, COCINA, PREPARADO, ENTREGADO
}


@Entity
data class Pedido(

        var fechaPedido: LocalDate,

        var totalPedido: Double,

        @Enumerated(EnumType.STRING)
        var estado: Estado,

        var favorito: Boolean = false,

        var horaRecogida: LocalTime? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        var user: User? = null,

        @JsonBackReference
        @ManyToOne
        var bar: Bar? = null,

        var comentario: String? = "",

        @JsonManagedReference
        @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
        var lineasPedido: MutableList<LineaPedido>? = ArrayList(),

        @Id @GeneratedValue
        val id: UUID? = null
) {
}