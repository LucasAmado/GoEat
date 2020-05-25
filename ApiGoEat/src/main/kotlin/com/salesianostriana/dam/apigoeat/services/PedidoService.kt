package com.salesianostriana.dam.apigoeat.services

import com.salesianostriana.dam.apigoeat.models.LineaPedido
import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.repos.PedidoRepository
import java.util.*

class PedidoService : BaseService<Pedido, UUID, PedidoRepository>() {

    fun findByUser(user: User) = this.repository.findByUser(user)
}