package com.lucasamado.goeatapp.models.bar

data class BarDetail(
    val foto: String,
    val id: String,
    val nombre: String,
    val platos: List<Plato>,
    val tipoComida: String
)