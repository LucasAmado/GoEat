package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.LineaPedido
import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.models.dtos.LineaPedidoDTO
import com.salesianostriana.dam.apigoeat.models.dtos.toLineaPedidoDto
import com.salesianostriana.dam.apigoeat.services.CarritoService
import com.salesianostriana.dam.apigoeat.services.LineaPedidoService
import com.salesianostriana.dam.apigoeat.services.PedidoService
import com.salesianostriana.dam.apigoeat.services.PlatoService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/pedidos")
class PedidoController(val pedidoService: PedidoService, val platoService: PlatoService, val carritoService: CarritoService, val lineaPedidoService: LineaPedidoService) {

    @PostMapping("/actualizar-carrito/{cantidad}/{id}")
    fun modificarCarrito(@PathVariable("cantidad") cantidad: Int, @PathVariable("id") id: UUID): LineaPedidoDTO{
        var platoFind = platoService.findById(id)

        return carritoService.actualizarCarrito(cantidad, platoFind.get()).toLineaPedidoDto()
    }

    @DeleteMapping("/borrar-plato/{id}")
    fun borrarPlatoCarrito(@PathVariable("id") id: UUID): Boolean = carritoService.borrarPlato(id)

    @GetMapping("/ver-carrito")
    fun verCarrito(): List<LineaPedidoDTO> = carritoService.lineasCarrito.map { it.toLineaPedidoDto() }

    @GetMapping("/tamanyo-carrito")
    fun consultarTamanyo(): Int = carritoService.lineasCarrito.size

    @PostMapping("/limpiar-carrito")
    fun limpiarCarrito() = carritoService.lineasCarrito.clear()

    @GetMapping("/calcular/total-carrito")
    fun CalcularTotalCarrito(): Double = carritoService.calcularTotalCompra()

    //TODO pasar por el body LocalTime hora recogida y bar?
    @GetMapping("/pagar")
    fun pagarPedido(@AuthenticationPrincipal usuario: User): Boolean {
        val carrito: List<LineaPedido> = carritoService.lineasCarrito
        var pagoCorrecto = false

        if (carrito.isNotEmpty()) {
            var pedidoNuevo = Pedido(LocalDate.now(), 0.0, false)
            for (lp in carrito) {
                with(pedidoNuevo) {
                    totalPedido = carritoService.calcularTotalCompra()
                    lineasPedido?.addAll(carrito)
                    user = usuario
                    //TODO pensar en como pasarle el bar
                    //bar =
                    pedidoService.save(this)
                    pagoCorrecto = true
                }
            }
        }
        return pagoCorrecto
    }
}