package com.salesianostriana.dam.apigoeat.repos

import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PedidoRepository: JpaRepository<Pedido, UUID> {

    fun findByUser(user: User): List<Pedido>
}