package com.salesianostriana.dam.apigoeat.repos

import com.salesianostriana.dam.apigoeat.models.Bar
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalTime
import java.util.*

interface BarRepository: JpaRepository<Bar, UUID> {

    @Query("select distinct tipoComida from Bar b")
    fun findTiposComida(): List<String>


    @Query("select b from Bar b where b.tipoComida = :tipoComida")
    fun findBaresByTipoComida(tipoComida: String): List<Bar>

}