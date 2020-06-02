package com.lucasamado.goeatapp.models.bar

data class EditarBar(
    val horaApertura: String,
    val horaCierre: String,
    val nombre: String,
    val tiempoPedido: String,
    val tipoComida: String
)