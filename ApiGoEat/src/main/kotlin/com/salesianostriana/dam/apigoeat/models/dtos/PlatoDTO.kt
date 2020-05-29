package com.salesianostriana.dam.apigoeat.models.dtos

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.LineaPedido
import com.salesianostriana.dam.apigoeat.models.Plato
import java.util.*
import javax.persistence.*

data class PlatoDTO(
        var nombre: String,
        var foto: String,
        var precioU: Double,
        var descripcion: String,
        var tipo: String,
        var bar: BarDTO? = null,
        val id : UUID? = null
)

fun Plato.toPlatoDTO() = PlatoDTO(nombre, foto, precioU, descripcion, tipo, bar?.toBarDTO(), id)