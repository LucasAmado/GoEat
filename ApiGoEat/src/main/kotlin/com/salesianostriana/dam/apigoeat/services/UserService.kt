package com.salesianostriana.dam.apigoeat.services

import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.repos.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val encoder: PasswordEncoder): BaseService<User, UUID, UserRepository>() {

    fun findByEmail(username: String) = this.repository.findByEmail(username)

}