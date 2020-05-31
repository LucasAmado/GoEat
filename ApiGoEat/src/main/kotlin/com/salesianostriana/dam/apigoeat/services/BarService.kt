package com.salesianostriana.dam.apigoeat.services

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.Pedido
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

    fun consultarHorario(id: UUID, pedidos: List<Pedido>): List<LocalTime> {
        var bar:Bar = this.findById(id).get()
        //var pedidos: List<Pedido> = pedidoService.findByBarAndToday(id)
        var horas: MutableList<LocalTime> = mutableListOf()
        horas.addAll(bar.horasDisponibles!!)

        for(h in bar.horasDisponibles!!){
            for(p in pedidos){
                if(p.horaRecogida == h || h.isBefore(LocalTime.now().plusMinutes(bar.tiempoPedido))){
                    horas.remove(h)
                }
            }
        }

        bar.horasDisponibles = horas
        this.save(bar)

        return horas
    }

}