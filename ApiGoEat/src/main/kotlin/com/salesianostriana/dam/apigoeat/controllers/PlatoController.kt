package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.services.BarService
import com.salesianostriana.dam.apigoeat.services.PlatoService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/platos")
class PlatoController(val platoService: PlatoService, val barService: BarService) {

    @GetMapping("/tipos/{id}")
    fun finTiposByIdBar(@PathVariable id : UUID): List<String> = platoService.findTiposByIdBar(id)

    @GetMapping("/{tipo}/bar/{id}")
    fun findByTipoAndBar(@PathVariable tipo : String, @PathVariable id : UUID) = platoService.findByTipoAndBar(tipo, id)
}