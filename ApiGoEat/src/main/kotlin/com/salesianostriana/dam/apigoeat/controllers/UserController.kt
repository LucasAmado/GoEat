package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.models.dtos.EditUserDto
import com.salesianostriana.dam.apigoeat.models.dtos.UserDTO
import com.salesianostriana.dam.apigoeat.models.dtos.toUserDTO
import com.salesianostriana.dam.apigoeat.services.UserService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {

    @ApiOperation(value = "Mi perfil", notes = "Se devuelven los datos del usuario logeado")
    @GetMapping("/me")
    fun me(@ApiParam(value = "usuario logeado", required = true, type = "User")
           @AuthenticationPrincipal user: User) = user.toUserDTO()

    @ApiOperation(value = "Editar mi perfil", notes = "Se modifican los datos del usuario logeado a partir de los nuevos que se envían")
    @PutMapping("/me/edit")
    fun editMe(@ApiParam(value = "usuario logeado", required = true, type = "User")
               @AuthenticationPrincipal user: User,
               @ApiParam(value = "nuevos datos", required = true, type = "EditUserDto")
               @RequestBody editUserDto: EditUserDto): UserDTO {
        with(editUserDto) {
            user.nombreCompleto = nombreCompleto
            user.email = email
            user.nickName = nickName
            user.fechaCambio = LocalDate.now()
        }
        userService.edit(user)

        return user.toUserDTO()
    }
}