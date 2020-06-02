package com.lucasamado.goeatapp.models.bar

data class BarDto(
    val foto: String,
    val horaApertura: String,
    val horaCierre: String,
    val id: String,
    val nombre: String,
    val tiempoPedido: Int,
    val tipoComida: String
)