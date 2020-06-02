package com.salesianostriana.dam.apigoeat.models.dtos

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.Plato
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

data class BarDTO(
        var nombre : String,
        var tipoComida: String,
        var foto: String,
        var horaApertura: LocalTime,
        var horaCierre: LocalTime,
        var tiempoPedido: Long,
        val id: UUID? = null
)


fun Bar.toBarDTO() = BarDTO(nombre, tipoComida, foto, horaApertura, horaCierre, tiempoPedido, id)

data class CreateBarDTO(
        var nombre : String,
        var tipoComida: String,
        var foto: String,
        var latitud: Double,
        var longitud: Double,
        var horaApertura: LocalTime,
        var horaCierre: LocalTime,
        var tiempoPedido: Long
)

fun CreateBarDTO.toBar() = Bar(nombre, tipoComida, foto, latitud, longitud, horaApertura, horaCierre, tiempoPedido)

data class BarDetailDTO(
        var nombre : String,
        var tipoComida: String,
        var foto: String,
        var latitud: Double,
        var longitud: Double,
        var horaApertura: LocalTime,
        var horaCierre: LocalTime,
        var platos: MutableList<Plato>? = ArrayList(),
        var horasDisponibles: MutableList<LocalTime>? = ArrayList(),
        var id: UUID? = null
)

fun Bar.toBarDetailDTO() = BarDetailDTO(nombre, tipoComida, foto, latitud, longitud, horaApertura, horaCierre,platos, horasDisponibles, id)

data class EditarBarDTO(
        var nombre : String,
        var tipoComida: String,
        var horaApertura: LocalTime,
        var horaCierre: LocalTime,
        var tiempoPedido: Long
)