package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.dtos.LineaPedidoDTO
import com.salesianostriana.dam.apigoeat.models.dtos.toLineaPedidoDto
import com.salesianostriana.dam.apigoeat.services.LineaPedidoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/lineas")
class LineasPedidoController(val lineaPedidoService: LineaPedidoService) {

    @GetMapping("/")
    fun getLineas(): List<LineaPedidoDTO> = lineaPedidoService.findAll().map {
        it.toLineaPedidoDto()
    }
}