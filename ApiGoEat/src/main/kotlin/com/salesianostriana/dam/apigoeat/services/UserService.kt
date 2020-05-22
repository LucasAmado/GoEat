package com.salesianostriana.dam.apigoeat.services

import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.models.dtos.CreateUserDTO
import com.salesianostriana.dam.apigoeat.models.dtos.toUser
import com.salesianostriana.dam.apigoeat.repos.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val encoder: PasswordEncoder): BaseService<User, UUID, UserRepository>() {

    fun findByUsername(username: String) = this.repository.findByUsername(username)

    fun create(createUser: CreateUserDTO): Optional<User> {
        if (findByUsername(createUser.username).isPresent) return Optional.empty()

        return Optional.of(
                this.save(createUser.toUser())
        )
    }
}