package com.lucasamado.goeatapp.models.pedido.prueba

data class Plato(
    val descripcion: String,
    val foto: String,
    val id: String,
    val lineasPedido: List<Any>,
    val nombre: String,
    val precioU: Double,
    val tipo: String
)