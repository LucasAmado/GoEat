package com.salesianostriana.dam.apigoeat.repos

import com.salesianostriana.dam.apigoeat.models.Bar
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BarRepository: JpaRepository<Bar, UUID> {


}