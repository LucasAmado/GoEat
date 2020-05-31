package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.models.dtos.*
import com.salesianostriana.dam.apigoeat.services.BarService
import com.salesianostriana.dam.apigoeat.services.PedidoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalTime
import java.util.*

@RestController
@RequestMapping("/bares")
class BarController(val barService: BarService, val pedidoService: PedidoService) {

    private fun allBares(): List<Bar> {
        var result: List<Bar> = barService.findAll()

        if (result.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No hay bares")
        return result
    }

    private fun oneBar(id: UUID): BarDetailDTO {
        var result: Optional<Bar> = barService.findById(id)

        if (result.isPresent) return result.get().toBarDetailDTO()
        else throw  ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el bar con id $id")
    }

    private fun findTipos(): List<String> {
        var result: List<String> = barService.findAllTiposComida()

        if (result.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No hay tipos de comidas")
        return result
    }

    @GetMapping("/consultar/horarios-recogida/{id}")
    fun consultarHorarios(@PathVariable id: UUID): List<LocalTime>{
        var pedidos: List<Pedido> = pedidoService.findByBarAndToday(id)
        return barService.consultarHorario(id, pedidos)
    }


    @GetMapping("/")
    fun listarBares() = barService.findAbiertos()

    @GetMapping("/tipos")
    fun tiposComida() = findTipos()


    @PostMapping("/")
    fun crearBar(@RequestBody nuevoBar: CreateBarDTO, @AuthenticationPrincipal owner: User): ResponseEntity<BarDTO> =
            ResponseEntity.status(HttpStatus.CREATED).body(barService.save(nuevoBar.toBar()).toBarDTO())

    @GetMapping("/{id}")
    fun detailBar(@PathVariable id: UUID) = oneBar(id)


}