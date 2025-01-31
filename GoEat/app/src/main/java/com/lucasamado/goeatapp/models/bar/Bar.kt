package com.lucasamado.goeatapp.models.bar

import com.lucasamado.goeatapp.models.plato.Plato

data class Bar(
    val foto: String,
    val horaApertura: String,
    val horaCierre: String,
    val horasDisponibles: List<String>,
    val id: String,
    val latitud: Double,
    val longitud: Double,
    val nombre: String,
    val pedidos: List<Any>,
    val platos: List<Plato>,
    val tiempoPedido: Int,
    val tipoComida: String
)