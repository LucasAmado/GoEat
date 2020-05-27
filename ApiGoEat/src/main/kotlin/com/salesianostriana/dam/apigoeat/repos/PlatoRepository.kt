package com.salesianostriana.dam.apigoeat.repos

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.Plato
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface PlatoRepository: JpaRepository<Plato, UUID> {

    @Query("select p from Plato p where p.bar.id = :idBar")
    fun findByBar(idBar: UUID): List<Plato>

    @Query("select distinct tipo from Plato p where p.bar.id = :idBar")
    fun findTiposByBar(idBar: UUID): List<String>

    @Query("select p from Plato p where p.tipo = :tipoPlato and p.bar.id = :idBar")
    fun findByTipoAndBar(tipoPlato: String, idBar: UUID): List<Plato>

}