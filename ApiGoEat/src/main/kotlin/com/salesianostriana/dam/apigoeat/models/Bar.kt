package com.salesianostriana.dam.apigoeat.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import kotlin.collections.ArrayList

@Entity
class Bar(

        var nombre: String,

        var tipoComida: String,

        var foto: String,

        var latitud: Double,

        var longitud: Double,

        var horaApertura: LocalTime,

        var horaCierre: LocalTime,

        var tiempoPedido: Long,

        @JsonManagedReference
        @OneToMany(mappedBy = "bar", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
        @Fetch(value = FetchMode.SUBSELECT)
        var platos: MutableList<Plato>? = ArrayList(),

        @ElementCollection(fetch = FetchType.EAGER)
        @Fetch(value = FetchMode.SUBSELECT)
        var horasDisponibles: MutableList<LocalTime>? = ArrayList(),

        @Id @GeneratedValue
        val id : UUID? = null
) {

}