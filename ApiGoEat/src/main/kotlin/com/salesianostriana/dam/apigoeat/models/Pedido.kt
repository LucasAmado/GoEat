package com.salesianostriana.dam.apigoeat.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.persistence.*

@Entity
data class Pedido(

        var fechaPedido: LocalDate,

        var totalPedido: Double,

        var favorito: Boolean = false,

        //TODO pasarselo al controller con un @Body
        var horaRegodida: LocalTime? = null,

        @JsonBackReference
        @ManyToOne
        var user: User? = null,

        @JsonBackReference
        @ManyToOne
        var bar: Bar? = null,

        @JsonManagedReference
        @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
        var lineasPedido: MutableList<LineaPedido>? = ArrayList(),

        @Id @GeneratedValue
        val id : UUID? = null
) {
}