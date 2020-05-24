package com.lucasamado.goeatapp.models.bar

import com.lucasamado.goeatapp.models.plato.Plato

data class Bar(
    val cantPedidos: Int,
    val foto: String,
    val horaApertura: String,
    val horaCierre: String,
    val id: String,
    val latitud: Double,
    val longitud: Double,
    val nombre: String,
    val pedidos: List<Any>,
    val platos: List<Plato>,
    val tiempoPedidos: Int,
    val tipoComida: String
)