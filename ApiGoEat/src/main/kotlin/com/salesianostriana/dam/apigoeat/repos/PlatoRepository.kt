package com.salesianostriana.dam.apigoeat.repos

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.Plato
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PlatoRepository: JpaRepository<Plato, UUID> {

    fun findByBar(bar: Bar): List<Plato>
}