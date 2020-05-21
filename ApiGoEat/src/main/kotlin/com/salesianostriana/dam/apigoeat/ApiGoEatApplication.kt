package com.salesianostriana.dam.apigoeat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.access.prepost.PreAuthorize

@SpringBootApplication
class ApiGoEatApplication

fun main(args: Array<String>) {
	runApplication<ApiGoEatApplication>(*args)
}
