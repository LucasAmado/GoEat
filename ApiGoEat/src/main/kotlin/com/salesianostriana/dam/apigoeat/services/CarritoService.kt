package com.salesianostriana.dam.apigoeat.services

import com.salesianostriana.dam.apigoeat.models.LineaPedido
import com.salesianostriana.dam.apigoeat.models.Plato
import java.util.*
import kotlin.collections.ArrayList

class CarritoService {
    var platoService: PlatoService = PlatoService()
    var lineasCarrito: MutableList<LineaPedido> = ArrayList()

    //TODO plato o id plato?
    fun addModificarPlato(cantidad: Int, p: Plato) {
        var lineaExist = false
        for (lp in lineasCarrito) {
            if (lp.plato?.id == p.id) {
                lp.cantidad = cantidad
                lp.totalLinea = lp.calcularPrecioLinea(cantidad)
                lineaExist = true
            }
        }
        if (!lineaExist) {
            lineasCarrito.add(LineaPedido(1, p.precioU, p))
        }
    }

    fun deletePlato(p: Plato) {
        for (lp in lineasCarrito) {
            if (lp.plato == p) {
                lineasCarrito.remove(lp)
            }
        }
    }

    /**
     * si la cantidad es cero entonces delete=true
     * se recorre la lista de productos, y si se encuentra con una lineaPedido existente entonces actualiza los datos
     * si al recorre la lista delete=true entonces se borrra la lineaPedido
     * Si por el contrario la LineaPedido no existe y !delete entonces se crea la LineaPedido, con los datos correspondientes
     */
    fun actualizarCarrito(cantidad: Int, id: UUID) {
        var delete = false
        var lineaExist = false

        if (cantidad == 0) delete = true
        loop@ for (lp in lineasCarrito) {
            if (lp.plato?.id == id) {
                lp.cantidad = cantidad
                lp.totalLinea = lp.calcularPrecioLinea(cantidad)
                lineaExist = true

                if (delete) {
                    lineasCarrito.remove(lp)
                }
                break@loop
            }
        }
        if (!lineaExist && !delete) {
            var p = platoService.findById(id)
            lineasCarrito.add(LineaPedido(cantidad, p.get().precioU * cantidad, p.get()))
        }

    }


    fun calcularTotalCompra(): Double {
        var total = 0.0
        for (lp in lineasCarrito) {
            total += lp.calcularPrecioLinea(lp.cantidad)
        }
        return total
    }

    fun cleanCarrito() {
        lineasCarrito.clear()
    }
}