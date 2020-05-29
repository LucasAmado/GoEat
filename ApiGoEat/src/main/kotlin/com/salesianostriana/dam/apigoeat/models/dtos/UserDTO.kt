package com.salesianostriana.dam.apigoeat.models.dtos

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import java.util.*
import kotlin.collections.ArrayList

data class UserDTO(
        var username : String,
        var email: String,
        var avatar: String,
        var roles: String,
        val id: UUID? = null,
        var bar: Bar? = null

)

fun User.toUserDTO() = UserDTO(username, email, avatar, roles.joinToString(), id, bar)

data class CreateUserDTO(
        var username: String,
        var email: String,
        var password: String,
        val password2: String
)

fun CreateUserDTO.toUser() = User(username, email, password)
