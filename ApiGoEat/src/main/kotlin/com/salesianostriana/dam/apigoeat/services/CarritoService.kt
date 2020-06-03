package com.salesianostriana.dam.apigoeat.services

import com.salesianostriana.dam.apigoeat.models.*
import com.salesianostriana.dam.apigoeat.models.dtos.LineaPedidoDTO
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
class CarritoService {
    var lineasCarrito: MutableList<LineaPedido> = ArrayList()


    fun calcularTotalCompra(): Double {
        var total = 0.0
        for (lp in lineasCarrito) {
            total += lp.calcularPrecioLinea()
        }
        return total
    }

    /**
     * se recorre la lista de productos, y si se encuentra con una lineaPedido existente entonces actualiza los datos
     * Si por el contrario la LineaPedido no existe se crea la LineaPedido, con los datos correspondientes.
     * Y si se pide comida a otro bar el carrito se vacía y se crea una lineaPedido
     */
    fun actualizarCarrito(cantidad: Int, plato: Plato): LineaPedido {
        var lineaExist = false
        lateinit var lineaPedido: LineaPedido

        loop@ for (lp in lineasCarrito) {
            //Si se pide comida a otro bar
            if (lp.plato?.bar?.id != plato.bar?.id) {
                lineasCarrito.clear()
                break@loop
            }

            if (lp.plato?.id == plato.id) {
                lp.cantidad = cantidad
                lp.totalLinea = lp.calcularPrecioLinea()
                lineaExist = true

                lineaPedido = lp
                break@loop
            }
        }
        if (!lineaExist) {
            var lp = LineaPedido(cantidad, plato.precioU * cantidad, plato)
            lineasCarrito.add(lp)
            lineaPedido = lp
        }

        return lineaPedido
    }


    fun borrarPlato(plato: Plato): Boolean {
        var delete = false
        loop@for (lp in lineasCarrito) {
            if (lp.plato?.id == plato.id) {
                lineasCarrito.remove(lp)
                delete = true
                break@loop
            }
        }

        return delete
    }
}