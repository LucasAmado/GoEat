package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.Estado
import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.models.dtos.*
import com.salesianostriana.dam.apigoeat.services.BarService
import com.salesianostriana.dam.apigoeat.services.LineaPedidoService
import com.salesianostriana.dam.apigoeat.services.PedidoService
import com.salesianostriana.dam.apigoeat.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/admin")
class AdminController(val userService: UserService, val pedidoService: PedidoService, val barService: BarService){
    private fun allUsers() : List<User> {
        var result: List<User> = userService.findAll()

        if (result.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No hay bares")
        return result
    }

    private fun oneBar(id: UUID): User {
        var result: Optional<User> = userService.findById(id)

        if(result.isPresent) return result.get()
        else throw  ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el bar con id $id")
    }

    @GetMapping("/users/")
    fun listarUsuarios() = allUsers().map {
        it.toUserDTO()
    }

    @GetMapping("/pedidos/hoy-bar")
    fun pedidosBar(@AuthenticationPrincipal user: User): List<PedidoDetalleDTO> {
        var idBar: UUID = user.bar?.id!!
        return pedidoService.findByBarAndToday(idBar).map { it.toPedidoDetalleDTO() }
    }

    @PutMapping("/estado-pedido/{id}")
    fun cambiarEstado(@PathVariable("id") id: UUID): Estado = pedidoService.cambiarEstado(id)

    @GetMapping("/mi-bar")
    fun getMyBar(@AuthenticationPrincipal user: User): BarDTO = user.bar!!.toBarDTO()

    @PutMapping("/editar/mi-bar")
    fun editarBar(@AuthenticationPrincipal user: User, @RequestBody editarBarDTO: EditarBarDTO): BarDTO =
            barService.editarBar(user, editarBarDTO).toBarDTO()
}