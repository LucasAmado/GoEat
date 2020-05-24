package com.salesianostriana.dam.apigoeat.services

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.Plato
import com.salesianostriana.dam.apigoeat.repos.PlatoRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlatoService : BaseService<Plato, UUID, PlatoRepository>(){

    fun findTiposByIdBar(id: UUID): List<String> = repository.findTiposByBar(id)

    fun findByTipoAndBar(tipo: String, id: UUID): List<Plato> = repository.findByTipoAndBar(tipo, id)

}