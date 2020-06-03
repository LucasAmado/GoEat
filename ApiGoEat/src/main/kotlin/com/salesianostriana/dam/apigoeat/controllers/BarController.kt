package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.models.dtos.*
import com.salesianostriana.dam.apigoeat.services.BarService
import com.salesianostriana.dam.apigoeat.services.PedidoService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalTime
import java.util.*

@RestController
@RequestMapping("/bares")
class BarController(val barService: BarService, val pedidoService: PedidoService) {

    private fun allBaresAbiertos(): List<Bar> {
        var result: List<Bar> = barService.findAbiertos()

        if (result.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No hay bares abiertos")
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

    private fun findByTipoComida(tipoComida: String): List<Bar> {
        var result: List<Bar> = barService.findByTipoComida(tipoComida)
        if (result.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No hay bares con ese tipo de comida")
        return result
    }

    @ApiOperation(value = "encontrar todos los bares abiertos", notes = "se recorren todos los bares y se comprueba si la hora actual esté entre la de apertura y cierre de cada bar")
    @GetMapping("/")
    fun listarBares() = allBaresAbiertos()

    @ApiOperation(value = "consultar las horas de recogida disponibles de un bar", notes = "Pasando el id del bar como parámtro se buscan tosos los pedidos que hay para ese bar en ese día. Luego se recorre la lista de horas disponibles que inicialmente tiene el bar y se descartan aquellas en las que ya haya alguna reserva y las que hayan pasado la hora actual")
    @GetMapping("/consultar/horarios-recogida/{id}")
    fun consultarHorarios(@ApiParam(value = "id del bar", required = true, type = "UUID")
                          @PathVariable id: UUID): List<LocalTime> {
        var pedidos: List<Pedido> = pedidoService.findByBarAndToday(id)
        return barService.consultarHorario(id, pedidos)
    }

    @ApiOperation(value = "Encontrar los tipos de comida", notes = "Se buscan todos los tipos de comida que hay en los bares")
    @GetMapping("/tipos")
    fun tiposComida() = findTipos()


    @PostMapping("/")
    @ApiOperation(value = "crear un bar")
    fun crearBar(@ApiParam(value = "datos del nuevo bar", required = true, type = "CreateBarDTO")
                 @RequestBody nuevoBar: CreateBarDTO, @AuthenticationPrincipal owner: User): ResponseEntity<BarDTO> =
            ResponseEntity.status(HttpStatus.CREATED).body(barService.save(nuevoBar.toBar()).toBarDTO())

    @ApiOperation(value = "encontrar un bar", notes = "se busca un bar en concreto a partir de su id")
    @GetMapping("/{id}")
    fun detailBar(@ApiParam(value = "id del bar", required = true, type = "UUID")
                  @PathVariable id: UUID) = oneBar(id)

    @ApiOperation(value = "cargar horarios", notes = "Al arrancar la aplicación se hace un cálculo automático de las horas de recogida disponible")
    @GetMapping("/cargar-horarios")
    fun disponibilidad() = barService.cargarHorarios()

    @ApiOperation(value = "Bar por tipo de comida", notes = "Lista de bares por el tipo de comida seleccionado")
    @GetMapping("/tipo-comida/{tipoComida}")
    fun getBaresByTipoComida(@ApiParam(value = "tipo de comida", required = true, type = "String", example = "Pizzas")
                             @PathVariable("tipoComida") tipoComida: String): List<Bar> = findByTipoComida(tipoComida)

}