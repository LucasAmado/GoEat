package com.salesianostriana.dam.apigoeat.models.dtos

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.Plato
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

data class BarDTO(
        var nombre : String,
        var tipoComida: String,
        var foto: String,
        //var horasDisponibles: MutableList<LocalDateTime>? = null,
        val id: UUID? = null
)


fun Bar.toBarDTO() = BarDTO(nombre, tipoComida, foto, //horasDisponibles,
id)

data class CreateBarDTO(
        var nombre : String,
        var tipoComida: String,
        var foto: String,
        var latitud: Double,
        var longitud: Double,
        var horaApertura: LocalTime,
        var horaCierre: LocalTime,
        var cantPedidos: Int,
        var tiempoPedidos: Int,
        //var horasDisponibles: MutableList<LocalDateTime> = ArrayList(),
        var platos: MutableList<Plato>? = null
)

fun CreateBarDTO.toBar() = Bar(nombre, tipoComida, foto, latitud, longitud, horaApertura, horaCierre, cantPedidos, tiempoPedidos, //ArrayList(),
platos)

data class BarDetailDTO(
        var nombre : String,
        var tipoComida: String,
        var foto: String,
        var horaApertura: LocalTime,
        var horaCierre: LocalTime,
        var cantPedidos: Int,
        var tiempoPedidos: Int,
        var platos: MutableList<Plato>? = ArrayList(),
        var pedidos: MutableList<Pedido>? = ArrayList()
)

fun Bar.toBarDetailDTO() = BarDetailDTO(nombre, tipoComida, foto, horaApertura, horaCierre, cantPedidos, tiempoPedidos, platos, pedidos)