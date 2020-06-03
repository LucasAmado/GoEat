package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.Estado
import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.models.dtos.*
import com.salesianostriana.dam.apigoeat.services.BarService
import com.salesianostriana.dam.apigoeat.services.LineaPedidoService
import com.salesianostriana.dam.apigoeat.services.PedidoService
import com.salesianostriana.dam.apigoeat.services.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/admin")
class AdminController(val userService: UserService, val pedidoService: PedidoService, val barService: BarService) {
    private fun allUsers(): List<User> {
        var result: List<User> = userService.findAll()

        if (result.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No hay bares")
        return result
    }

    private fun allPedidos(user: User): List<PedidoDetalleDTO> {
        var idBar: UUID = user.bar?.id!!
        var result: List<Pedido> = pedidoService.findByBarAndToday(idBar)

        if (result.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No hay pedidos")
        return result.map { it.toPedidoDetalleDTO() }
    }


    @ApiOperation(value = "usuarios", notes = "encontrar todos los usuarios que hay")
    @GetMapping("/users/")
    fun listarUsuarios() = allUsers().map {
        it.toUserDTO()
    }

    @ApiOperation(value = "pedidos de hoy de un bar", notes = "Se buscan todos los pedidos que tiene para hoy el bar del usuario logeado")
    @GetMapping("/pedidos/hoy-bar")
    fun pedidosBar(@ApiParam(value = "usuario logeado", required = true, type = "User")
                   @AuthenticationPrincipal user: User): List<PedidoDetalleDTO> = allPedidos(user)

    @ApiOperation(value = "modificar el estado de un pedido", notes = "A partir de su id se puede cambiar el estado en el que se encuentra el pedido")
    @PutMapping("/estado-pedido/{id}")
    fun cambiarEstado(@ApiParam(value = "id del usuario", required = true, type = "UUID")
                      @PathVariable("id") id: UUID): Estado = pedidoService.cambiarEstado(id)

    @ApiOperation(value = "bar del usuario logeado", notes = "Se encuentra el bar a partir del usuario logeado")
    @GetMapping("/mi-bar")
    fun getMyBar(@ApiParam(value = "usuario logeado", required = true, type = "User")
                 @AuthenticationPrincipal user: User): BarDTO = user.bar!!.toBarDTO()


    @ApiOperation(value = "Editar bar", notes = "Se modifican los datos del bar que está asociado al usuario logeado")
    @PutMapping("/editar/mi-bar")
    fun editarBar(@ApiParam(value = "usuario logeado", required = true, type = "User")
                  @AuthenticationPrincipal user: User,
                  @ApiParam(value = "datos modificados", required = true, type = "editarBarDTO")
                  @RequestBody editarBarDTO: EditarBarDTO): BarDTO =
            barService.editarBar(user, editarBarDTO).toBarDTO()
}