package com.salesianostriana.dam.apigoeat.services

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.LineaPedido
import com.salesianostriana.dam.apigoeat.repos.BarRepository
import com.salesianostriana.dam.apigoeat.repos.LineaPedidoRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class LineaPedidoService : BaseService<LineaPedido, UUID, LineaPedidoRepository>(){

    fun saveAll(lineas: List<LineaPedido>) = this.repository.saveAll(lineas)

    fun findByPedidoId(id:UUID):List<LineaPedido> = this.repository.findByPedidoId(id)
}