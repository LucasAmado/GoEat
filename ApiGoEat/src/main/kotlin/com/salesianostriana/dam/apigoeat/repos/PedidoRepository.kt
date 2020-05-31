package com.salesianostriana.dam.apigoeat.repos

import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface PedidoRepository: JpaRepository<Pedido, UUID> {

    fun findByUserOrderByFechaPedidoDesc(user: User): List<Pedido>

    @Query("select p from Pedido p where p.bar.id = :idBar and p.fechaPedido = CURRENT_DATE")
    fun findByBarAndToday(idBar: UUID): List<Pedido>
}