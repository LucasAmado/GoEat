package com.salesianostriana.dam.apigoeat.controllers

import com.salesianostriana.dam.apigoeat.models.dtos.CreateUserDTO
import com.salesianostriana.dam.apigoeat.models.dtos.UserDTO
import com.salesianostriana.dam.apigoeat.models.dtos.toUserDTO
import com.salesianostriana.dam.apigoeat.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {


}