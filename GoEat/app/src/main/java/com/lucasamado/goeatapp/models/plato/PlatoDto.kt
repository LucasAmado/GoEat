package com.lucasamado.goeatapp.models.plato

import com.lucasamado.goeatapp.models.bar.BarDto

data class PlatoDto(
    val bar: BarDto,
    val descripcion: String,
    val foto: String,
    val id: String,
    val nombre: String,
    val precioU: Double,
    val tipo: String
)