package com.salesianostriana.dam.apigoeat.models.dtos

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import java.util.*

data class UserDTO(
        var username : String,
        var email: String,
        var avatar: String,
        var pedidos: List<Pedido>? = null,
        val id: UUID? = null,
        var bar: Bar? = null

)

fun User.toUserDTO() = UserDTO(username, email, avatar, pedidos, id, bar)

data class CreateUserDTO(
        var username: String,
        var email: String,
        var password: String,
        val password2: String,
        var avatar: String
)

fun CreateUserDTO.toUser() = User(username, email, password, avatar)
