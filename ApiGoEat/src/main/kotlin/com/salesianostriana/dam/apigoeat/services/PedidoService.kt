package com.salesianostriana.dam.apigoeat.services

import com.salesianostriana.dam.apigoeat.models.Estado
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

    fun cambiarEstado(id: UUID): Estado{
        var pedido: Pedido = this.findById(id).get()
        with(pedido){
            var state = when{
                estado.equals(Estado.SOLICITADO) -> {
                    Estado.COCINA
                }
                estado.equals(Estado.COCINA) -> {
                    Estado.PREPARADO
                }
                else -> Estado.ENTREGADO
            }
            estado = state
            repository.save(this)
        }
        return pedido.estado
    }
}