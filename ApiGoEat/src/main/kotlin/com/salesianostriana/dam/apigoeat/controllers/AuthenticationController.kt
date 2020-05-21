package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.models.dtos.CreateUserDTO
import com.salesianostriana.dam.apigoeat.models.dtos.UserDTO
import com.salesianostriana.dam.apigoeat.models.dtos.toUser
import com.salesianostriana.dam.apigoeat.models.dtos.toUserDTO
import com.salesianostriana.dam.apigoeat.services.UserService
import com.salesianostriana.dam.apigoeat.security.JwtTokenProvider
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
class AuthenticationController(
        private val authenticationManager: AuthenticationManager,
        private val jwtTokenProvider: JwtTokenProvider,
        private val userService: UserService,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    @PostMapping("/auth/login")
    fun login(@Valid @RequestBody loginRequest: LoginRequest): JwtUserResponse {
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/me")
    fun me(@AuthenticationPrincipal user: User) = user.toUserDTO()

    @PostMapping("/signup")
    fun signup(@RequestBody user: CreateUserDTO): UserDTO{
        val result = userService.findByUsername(user.username)
        if(result!=null){
            user.password= bCryptPasswordEncoder.encode(user.password)
            return userService.save(user.toUser()).toUserDTO()
        } else {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario ${user.username} ya existe. Pruebe otro.")
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