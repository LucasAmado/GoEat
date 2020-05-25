package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.LineaPedido
import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.services.CarritoService
import com.salesianostriana.dam.apigoeat.services.PedidoService
import com.salesianostriana.dam.apigoeat.services.PlatoService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.*

@RestController
class PedidoController(val pedidoService: PedidoService, val platoService: PlatoService, val carritoService: CarritoService) {

    @GetMapping("/actualizar-carrito/{id}/{cantidad}")
    fun anadirProducto(@PathVariable("id") id: UUID, @PathVariable("cantidad") cantidad: Int) = carritoService.actualizarCarrito(cantidad, id)

    @GetMapping("/limpiar-carrito")
    fun limpiarCarrito() = carritoService.cleanCarrito()

    @ModelAttribute("total-carrito")
    fun CalcularTotalCarrito(): Double {
        val carrito: List<LineaPedido> = carritoService.lineasCarrito
        var total = 0.0
        if (carrito != null && carrito.isNotEmpty()) {
            for (lp in carrito) {
                total += lp.plato?.precioU!! * lp.cantidad
            }
        }
        return total
    }

    @GetMapping("/pedido/pagar")
    fun pagarPedido(@AuthenticationPrincipal usuario: User): Boolean {
        val carrito: List<LineaPedido> = carritoService.lineasCarrito
        var pagoCorrecto = false

        if (carrito.isNotEmpty()) {
            var pedidoNuevo = Pedido(LocalDate.now(), 0.0, false)
            for (lp in carrito) {
                with(pedidoNuevo) {
                    totalPedido = carritoService.calcularTotalCompra()
                    lineas?.addAll(carrito)
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