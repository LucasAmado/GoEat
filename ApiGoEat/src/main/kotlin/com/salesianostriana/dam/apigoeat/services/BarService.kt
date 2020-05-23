package com.salesianostriana.dam.apigoeat.services

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.repos.BarRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BarService: BaseService<Bar, UUID, BarRepository>(){

    fun getBares(){
        for(bar in this.findAll()){
        }
    }
}