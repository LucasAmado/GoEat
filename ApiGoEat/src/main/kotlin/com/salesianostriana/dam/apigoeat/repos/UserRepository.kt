package com.salesianostriana.dam.apigoeat.repos

import com.salesianostriana.dam.apigoeat.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, UUID> {

    fun findByUsername(username: String): Optional<User>
}