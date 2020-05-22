package com.salesianostriana.dam.apigoeat.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
class Bar(

        var nombre: String,

        var tipoComida: String,

        var foto: String,

        var latitud: Double,

        var longitud: Double,

        var horaApertura: LocalTime,

        var horaCierre: LocalTime,

        var cantPedidos: Int,

        var tiempoPedidos: Int,

        //var horasDisponibles: MutableList<LocalDateTime>? = null,

        @JsonManagedReference
        @OneToMany(mappedBy = "bar", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
        var platos: MutableList<Plato>? = ArrayList(),

        @JsonManagedReference
        @OneToMany(mappedBy = "bar", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
        var pedidos: MutableList<Pedido>? = ArrayList(),

        @Id @GeneratedValue
        val id : UUID? = null
) {
}