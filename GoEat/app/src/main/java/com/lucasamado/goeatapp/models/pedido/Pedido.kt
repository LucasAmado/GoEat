package com.lucasamado.goeatapp.models.pedido

data class Pedido(
    val favorito: Boolean,
    val fechaPedido: String,
    val horaRegodida: String,
    val id: String,
    val lineasPedido: List<LineaPedido>,
    val totalPedido: Double
)