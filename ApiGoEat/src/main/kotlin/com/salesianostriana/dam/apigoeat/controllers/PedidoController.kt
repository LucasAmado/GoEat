package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.LineaPedido
import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.models.dtos.*
import com.salesianostriana.dam.apigoeat.services.*
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("/pedidos")
class PedidoController(val pedidoService: PedidoService, val platoService: PlatoService, val carritoService: CarritoService, val barService: BarService, val lineaPedidoService: LineaPedidoService) {

    @PostMapping("/actualizar-carrito/{cantidad}/{id}")
    fun modificarCarrito(@PathVariable("cantidad") cantidad: Int, @PathVariable("id") id: UUID): LineaPedidoDTO {
        var platoFind = platoService.findById(id)

        return carritoService.actualizarCarrito(cantidad, platoFind.get()).toLineaPedidoDto()
    }

    @DeleteMapping("/borrar-plato/{id}")
    fun borrarPlatoCarrito(@PathVariable("id") id: UUID): Boolean = carritoService.borrarPlato(id)

    @GetMapping("/ver-carrito")
    fun verCarrito(): List<LineaPedidoDTO> = carritoService.lineasCarrito.map { it.toLineaPedidoDto() }

    @PostMapping("/limpiar-carrito")
    fun limpiarCarrito() = carritoService.lineasCarrito.clear()

    @GetMapping("/calcular/total-carrito")
    fun CalcularTotalCarrito(): Double = carritoService.calcularTotalCompra()


    @PostMapping("/pagar")
    fun pagarPedido(@AuthenticationPrincipal usuario: User, @RequestBody createPedidoDTO: CreatePedidoDTO): PedidoDTO? {
        val carrito: List<LineaPedido> = carritoService.lineasCarrito
        var pedidoNuevo: Pedido? = null

        println("hora: ${createPedidoDTO.horaRecogida}")
        println("comentario: ${createPedidoDTO.comentario}")

        if (carrito.isNotEmpty()) {
            var barId: UUID = carrito[0].plato?.bar!!.id!!
            var barFind: Bar = barService.findById(barId).get()

            pedidoNuevo = Pedido(LocalDate.now(), carritoService.calcularTotalCompra(), false, createPedidoDTO.horaRecogida, usuario, barFind, createPedidoDTO.comentario)
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

    @GetMapping("/ver/mis-pedidos")
    fun misPedidos(@AuthenticationPrincipal user: User): List<PedidoDTO> = pedidoService.findByUser(user).map { it.toPedidoDTO() }

    @GetMapping("/{id}")
    fun getPedido(@PathVariable("id") id: UUID): PedidoDTO = pedidoService.findById(id).get().toPedidoDTO()
}