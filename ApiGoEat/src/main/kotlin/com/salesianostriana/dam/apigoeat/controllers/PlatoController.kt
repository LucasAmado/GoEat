package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.Plato
import com.salesianostriana.dam.apigoeat.models.dtos.PlatoDTO
import com.salesianostriana.dam.apigoeat.models.dtos.toPlatoDTO
import com.salesianostriana.dam.apigoeat.services.BarService
import com.salesianostriana.dam.apigoeat.services.PlatoService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/platos")
class PlatoController(val platoService: PlatoService, val barService: BarService) {

    @ApiOperation(value = "Tipos de platos de cada bar", notes = "A partir del id del bar se buscan todos los tipos de platos que hay")
    @GetMapping("/tipos/{id}")
    fun finTiposByIdBar(@ApiParam(value = "id del bar", required = true, type = "UUID")
                        @PathVariable id: UUID): List<String> = platoService.findTiposByIdBar(id)

    @ApiOperation(value = "Platos de un bar y tipo", notes = "Se buscan todos los platos de un tipo concreto de un bar")
    @GetMapping("/tipo/{tipo}/bar/{id}")
    fun findByTipoAndBar(@ApiParam(value = "tipo de plato", required = true, type = "String", example = "Entrantes, postres, bebidas")
                         @PathVariable tipo: String,
                         @ApiParam(value = "id del bar", required = true, type = "UUID")
                         @PathVariable id: UUID): List<Plato> = platoService.findByTipoAndBar(tipo, id)

    @ApiOperation(value = "Encontrar un plato", notes = "Encontrar un plato a partir de su id")
    @GetMapping("/{id}")
    fun findById(@ApiParam(value = "id del plato", required = true, type = "UUID")
                 @PathVariable id: UUID): PlatoDTO = platoService.findById(id).get().toPlatoDTO()

    @ApiOperation(value = "Platos de un bar", notes = "Lista de platos de un bar")
    @GetMapping("/bar/{id}")
    fun findPlatosByBar(@ApiParam(value = "id del bar", required = true, type = "UUID")
                        @PathVariable id: UUID): List<Plato> = platoService.findByIdBar(id)
}