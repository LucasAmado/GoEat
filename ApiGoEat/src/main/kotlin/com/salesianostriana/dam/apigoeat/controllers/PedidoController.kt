package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.LineaPedido
import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.models.dtos.LineaPedidoDTO
import com.salesianostriana.dam.apigoeat.models.dtos.toLineaPedidoDto
import com.salesianostriana.dam.apigoeat.services.*
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/pedidos")
class PedidoController(val pedidoService: PedidoService, val platoService: PlatoService, val carritoService: CarritoService, val barService: BarService) {

    @PostMapping("/actualizar-carrito/{cantidad}/{id}")
    fun modificarCarrito(@PathVariable("cantidad") cantidad: Int, @PathVariable("id") id: UUID): LineaPedidoDTO{
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

    //TODO pasar por el body un pedido
    @GetMapping("/pagar")
    fun pagarPedido(@AuthenticationPrincipal usuario: User, @RequestBody pedido: Pedido): Pedido {
        val carrito: List<LineaPedido> = carritoService.lineasCarrito
        var pedidoNuevo = pedido

        if (carrito.isNotEmpty()) {
            var barFind = carrito[0].plato?.bar?.id?.let { barService.findById(it).get() }
            for (lp in carrito) {
                with(pedidoNuevo) {
                    totalPedido = carritoService.calcularTotalCompra()
                    lineasPedido?.addAll(carrito)
                    user = usuario
                    bar = barFind
                    pedidoService.save(this)
                }
            }
        }
        return pedidoNuevo
    }
}