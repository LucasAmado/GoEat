package com.lucasamado.goeatapp.models.bar

data class Bar(
    val foto: String,
    val horaApertura: String,
    val horaCierre: String,
    val id: String,
    val latitud: Double,
    val longitud: Double,
    val nombre: String,
    val platos: List<Plato>,
    val tipoComida: String
)