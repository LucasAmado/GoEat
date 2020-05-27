package com.lucasamado.goeatapp.models.plato

import com.lucasamado.goeatapp.models.pedido.LineaPedido

data class Plato(
    val descripcion: String,
    val foto: String,
    val id: String,
    val lineasPedido: List<LineaPedido>,
    val nombre: String,
    val precioU: Double,
    val tipo: String
)