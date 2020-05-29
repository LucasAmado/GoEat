package com.lucasamado.goeatapp.models.pedido

import java.time.LocalTime

data class CreatePedido(
    val comentario: String,
    val horaRecogida: String
)