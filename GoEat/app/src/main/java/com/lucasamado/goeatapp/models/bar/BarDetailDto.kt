package com.lucasamado.goeatapp.models.bar

import com.lucasamado.goeatapp.models.plato.Plato

data class BarDetailDto(
    val foto: String,
    val horaApertura: String,
    val horaCierre: String,
    val horasDisponibles: List<String>,
    val id: String,
    val latitud: Double,
    val longitud: Double,
    val nombre: String,
    val platos: List<Plato>,
    val tipoComida: String
)