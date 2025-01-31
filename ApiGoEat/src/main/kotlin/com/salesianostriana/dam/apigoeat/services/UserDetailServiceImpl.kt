package com.salesianostriana.dam.apigoeat.services

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service("userDetailsService")
class UserDetailServiceImpl(
        private val userService: UserService
) : UserDetailsService {


    override fun loadUserByUsername(username: String): UserDetails =
            userService.findByEmail(username).orElseThrow {
                UsernameNotFoundException("Usuario $username no encontrado")
            }
}