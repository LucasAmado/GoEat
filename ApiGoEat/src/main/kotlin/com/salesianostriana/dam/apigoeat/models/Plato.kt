package com.salesianostriana.dam.apigoeat.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.util.*
import javax.persistence.*

@Entity
data class Plato(

        var nombre: String,

        var foto: String,

        var precioU: Double,

        var descripcion: String,

        var tipo: String,

        @JsonBackReference
        @ManyToOne
        var bar: Bar? = null,

        @Id @GeneratedValue
        val id : UUID? = null
) {
}