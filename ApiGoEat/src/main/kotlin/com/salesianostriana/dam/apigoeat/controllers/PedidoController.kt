package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.*
import com.salesianostriana.dam.apigoeat.models.dtos.*
import com.salesianostriana.dam.apigoeat.services.*
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/pedidos")
class PedidoController(val pedidoService: PedidoService, val platoService: PlatoService, val carritoService: CarritoService, val barService: BarService, val lineaPedidoService: LineaPedidoService) {

    fun actualizarCarrito(cantidad: Int, id: UUID): LineaPedidoDTO {
        var plato: Optional<Plato> = platoService.findById(id)

        if (!plato.isPresent)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el plato con id $id")
        else if (carritoService.lineasCarrito.isEmpty())
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El carrito está vacío")
        else
            return carritoService.actualizarCarrito(cantidad, plato.get()).toLineaPedidoDto()
    }

    fun borrarPlato(id: UUID): Boolean {
        var plato: Optional<Plato> = platoService.findById(id)
        if (!plato.isPresent)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el plato con id $id y no se ha podido borrar")
        else if (carritoService.lineasCarrito.isEmpty())
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El carrito está vacío")
        else
            return carritoService.borrarPlato(plato.get())
    }

    fun findPedido(id: UUID): PedidoDTO {
        var result: Optional<Pedido> = pedidoService.findById(id)

        if (!result.isPresent)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el pedido con id $id")
        else
            return result.get().toPedidoDTO()
    }

    fun findLineasByPedido(id: UUID): List<LineaPedido>{
        var result: List<LineaPedido> = lineaPedidoService.findByPedidoId(id)
        if(result.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No hay líneas de pedido para este pedido")
        else
            return result
    }


    @ApiOperation(value = "Actualizar carrito", notes = "Se le pasa como parámetro el id del plato que se quiere comprar y la cantidad")
    @PostMapping("/actualizar-carrito/{cantidad}/{id}")
    fun modificarCarrito(@ApiParam(value = "cantidad del plato", required = true, type = "Int")
                         @PathVariable("cantidad") cantidad: Int, @PathVariable("id") id: UUID) = actualizarCarrito(cantidad, id)

    @ApiOperation(value = "Borrar un plato del carrito", notes = "Se le pasa como parámetro el id del plato que se quiere borrar.")
    @DeleteMapping("/borrar-plato/{id}")
    fun borrarPlatoCarrito(@ApiParam(value = "id del plato", required = true, type = "UUID")
                           @PathVariable("id") id: UUID): Boolean = borrarPlato(id)

    @ApiOperation(value = "Ver carrito", notes = "Se devuelven todas las líneas de pedido que hay en el carrito")
    @GetMapping("/ver-carrito")
    fun verCarrito(): List<LineaPedidoDTO> = carritoService.lineasCarrito.map { it.toLineaPedidoDto() }


    @ApiOperation(value = "Total de la compra", notes = "Se recorren todas las lineas de pedido y se suman sus precios")
    @GetMapping("/calcular/total-carrito")
    fun CalcularTotalCarrito(): Double = carritoService.calcularTotalCompra()


    @ApiOperation(value = "Pagar pedido", notes = "Sabiendo el usuario logeado y los datos requeridos se crea un pedido nuevo y se limpia el carrito")
    @PostMapping("/pagar")
    fun pagarPedido(@ApiParam(value = "usuario logeado", required = true, type = "User")
                    @AuthenticationPrincipal usuario: User,
                    @ApiParam(value = "datos para crear el pedido", required = true, type = "CreatePedidoDto")
                    @RequestBody createPedidoDTO: CreatePedidoDTO): PedidoDTO? {
        val carrito: List<LineaPedido> = carritoService.lineasCarrito
        var pedidoNuevo: Pedido? = null

        if (carrito.isNotEmpty()) {
            var barId: UUID = carrito[0].plato?.bar!!.id!!
            var barFind: Bar = barService.findById(barId).get()

            pedidoNuevo = Pedido(LocalDate.now(), carritoService.calcularTotalCompra(), Estado.SOLICITADO, false, createPedidoDTO.horaRecogida, usuario, barFind, createPedidoDTO.comentario)
            for (lp in carrito) {
                lp.pedido = pedidoNuevo
            }

            pedidoNuevo.lineasPedido!!.addAll(carrito)
            pedidoService.save(pedidoNuevo)
            lineaPedidoService.saveAll(carrito)
            carritoService.lineasCarrito.clear()
        }

        return pedidoNuevo?.toPedidoDTO()
    }

    @ApiOperation(value = "Mis pedidos", notes = "Se devuelven los pedidos del usuario logeado")
    @GetMapping("/ver/mis-pedidos")
    fun misPedidos(@ApiParam(value = "usuario logeado", required = true, type = "User")
                   @AuthenticationPrincipal user: User): List<PedidoDetalleDTO> = pedidoService.findByUser(user).map { it.toPedidoDetalleDTO() }

    @ApiOperation(value = "Encontrar pedido", notes = "Se devuelve el pedido buscado a partir de su id")
    @GetMapping("/{id}")
    fun getPedido(@ApiParam(value = "id del pedido", required = true, type = "UUID")
                  @PathVariable("id") id: UUID): PedidoDTO = findPedido(id)

    @ApiOperation(value = "Lineas de pedido", notes = "Se devuelven las líneas de pedido a partir de la id del pedido")
    @GetMapping("/lineas/{id}")
    fun getLineasPedidoByPedido(@ApiParam(value = "id del pedido", required = true, type = "UUID")
                                @PathVariable("id") id: UUID): List<LineaPedidoDetalleDTO> = findLineasByPedido(id).map { it.toLineaPedidoDetalleDTO() }

    @ApiOperation(value = "Cambiar pedido fav", notes = "Se le pasa como parámetro el id del pedido y se cambia el boolean favorito")
    @PutMapping("/{id}")
    fun editarPedido(@ApiParam(value = "id del pedido", required = true, type = "UUID")
                     @PathVariable("id") id: UUID): Boolean = pedidoService.editFavorito(id)
}