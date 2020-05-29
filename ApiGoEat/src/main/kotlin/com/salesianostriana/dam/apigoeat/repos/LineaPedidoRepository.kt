package com.salesianostriana.dam.apigoeat.repos

import com.salesianostriana.dam.apigoeat.models.LineaPedido
import com.salesianostriana.dam.apigoeat.models.Pedido
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface LineaPedidoRepository: JpaRepository<LineaPedido, UUID> {

    fun findByPedidoId(id: UUID): List<LineaPedido>
}