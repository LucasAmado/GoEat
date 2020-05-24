package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.dtos.BarDetailDTO
import com.salesianostriana.dam.apigoeat.models.dtos.toBar
import com.salesianostriana.dam.apigoeat.services.BarService
import com.salesianostriana.dam.apigoeat.services.PlatoService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/platos")
class PlatoController(val platoService: PlatoService, val barService: BarService) {

}