package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.models.dtos.CreateUserDTO
import com.salesianostriana.dam.apigoeat.models.dtos.UserDTO
import com.salesianostriana.dam.apigoeat.models.dtos.toUser
import com.salesianostriana.dam.apigoeat.models.dtos.toUserDTO
import com.salesianostriana.dam.apigoeat.services.UserService
import com.salesianostriana.dam.apigoeat.security.JwtTokenProvider
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
class AuthenticationController(
        private val authenticationManager: AuthenticationManager,
        private val jwtTokenProvider: JwtTokenProvider,
        private val userService: UserService,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    @ApiOperation(value = "Login", notes = "Se hace el login a partir de los datos enviados")
    @PostMapping("/auth/login")
    fun login(@ApiParam(value = "datos del usuario", required = true, type = "LoginRequest", example = "username: user, password: 123456")
              @Valid @RequestBody loginRequest: LoginRequest): JwtUserResponse {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginRequest.username, loginRequest.password
                )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val user = authentication.principal as User
        val jwtToken = jwtTokenProvider.generateToken(authentication)

        return JwtUserResponse(jwtToken, user.toUserDTO())

    }


    @ApiOperation(value = "Signup", notes = "creación de un nuevo usuario")
    @PostMapping("/signup")
    fun signup(@ApiParam(value = "datos del nuevo usuario", type = "createUserDTO", required = true)
            @RequestBody createUserDTO: CreateUserDTO): UserDTO{
        val result = userService.findByEmail(createUserDTO.email)
        if(result!=null){
            createUserDTO.password= bCryptPasswordEncoder.encode(createUserDTO.password)
            var newUser = createUserDTO.toUser()
            newUser.fechaCreacion = LocalDate.now()
            if(newUser.roles.isEmpty()){
                newUser.roles = mutableSetOf("USER")
            }
            return userService.save(newUser).toUserDTO()
        } else {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ${createUserDTO.email} ya existe. Pruebe otro.")
        }
    }
}


data class LoginRequest(
        @NotBlank val username: String,
        @NotBlank val password: String
)

data class JwtUserResponse(
        val token: String,
        val user: UserDTO
)