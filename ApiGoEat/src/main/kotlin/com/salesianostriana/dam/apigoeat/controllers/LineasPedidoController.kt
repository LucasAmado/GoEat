package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.dtos.*
import com.salesianostriana.dam.apigoeat.services.LineaPedidoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/lineas")
class LineasPedidoController(val lineaPedidoService: LineaPedidoService) {

    @GetMapping("/")
    fun getLineas(): List<LineaPedidoDTO> = lineaPedidoService.findAll().map {
        it.toLineaPedidoDto()
    }
}