package com.salesianostriana.dam.apigoeat.services

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.repos.BarRepository
import org.springframework.stereotype.Service
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

@Service
class BarService: BaseService<Bar, UUID, BarRepository>(){


    fun findAllTiposComida(): List<String>  = this.repository.findTiposComida()

    fun findAbiertos(): List<Bar> {
        var bares: MutableList<Bar> = ArrayList()
        var now = LocalTime.now()
        for(bar in this.repository.findAll()){
            if(bar.horaApertura.isBefore(now)&& bar.horaCierre.isAfter(now)){
                bares.add(bar)
            }
        }

        return bares
    }

}