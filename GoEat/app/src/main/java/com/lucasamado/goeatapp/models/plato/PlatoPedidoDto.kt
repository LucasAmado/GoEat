package com.lucasamado.goeatapp.models.plato

data class PlatoPedidoDto(
    val descripcion: String,
    val foto: String,
    val id: String,
    val nombre: String,
    val precioU: Double,
    val tipo: String
)