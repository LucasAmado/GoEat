package com.salesianostriana.dam.apigoeat.services

import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.repos.PedidoRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PedidoService : BaseService<Pedido, UUID, PedidoRepository>() {

    fun findByUser(user: User) = this.repository.findByUserOrderByFechaPedidoDesc(user)

    fun findByBarAndToday(id: UUID) = this.repository.findByBarAndToday(id)

    fun editFavorito(id: UUID): Boolean{
        var pedido = this.repository.findById(id).get()

        with(pedido){
            favorito = !favorito
            repository.save(this)
        }

        return pedido.favorito
    }
}