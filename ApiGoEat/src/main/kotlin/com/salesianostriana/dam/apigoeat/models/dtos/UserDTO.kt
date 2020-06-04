package com.salesianostriana.dam.apigoeat.models.dtos

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.User
import java.time.LocalDate
import java.util.*

data class UserDTO(
        var email: String,
        var nickName : String,
        var avatar: String? = "",
        var nombreCompleto: String,
        var roles: String,
        val id: UUID? = null,
        var fechaCreacion: LocalDate? = null,
        var fechaCambio: LocalDate? = null,
        var bar: Bar? = null
)

fun User.toUserDTO() = UserDTO(email, nickName, avatar, nombreCompleto, roles.joinToString(), id, fechaCreacion, fechaCambio, bar)

data class CreateUserDTO(
        val email: String,
        val nickName: String,
        val nombreCompleto: String,
        var password: String,
        val password2: String
)

fun CreateUserDTO.toUser() = User(email, nickName, nombreCompleto, password)

data class EditUserDto(
        val nombreCompleto: String,
        val email: String,
        val nickName: String
)
