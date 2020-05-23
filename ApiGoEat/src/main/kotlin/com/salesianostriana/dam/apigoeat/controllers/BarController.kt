package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.models.dtos.*
import com.salesianostriana.dam.apigoeat.services.BarService
import com.salesianostriana.dam.apigoeat.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/bares")
class BarController(val barService: BarService, val userService: UserService) {

    private fun allBares() : List<Bar> {
        var result: List<Bar> = barService.findAll()

        if (result.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No hay bares")
        return result
    }

    private fun oneBar(id: UUID): BarDetailDTO {
        var result: Optional<Bar> = barService.findById(id)

        if(result.isPresent) return result.get().toBarDetailDTO()
        else throw  ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el bar con id $id")
    }

    @GetMapping("/")
    fun listarBares() = barService.getBares()


    @PostMapping("/")
    fun crearNota(@RequestBody nuevoBar: CreateBarDTO, @AuthenticationPrincipal owner : User): ResponseEntity<BarDTO> {
        owner.bar = nuevoBar.toBar()
        //TODO guardar bar
        return ResponseEntity.status(HttpStatus.CREATED).body(barService.save(nuevoBar.toBar()).toBarDTO())
    }
}