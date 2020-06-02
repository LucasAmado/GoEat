package com.salesianostriana.dam.apigoeat.services

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.Pedido
import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.models.dtos.BarDetailDTO
import com.salesianostriana.dam.apigoeat.models.dtos.EditarBarDTO
import com.salesianostriana.dam.apigoeat.repos.BarRepository
import org.springframework.stereotype.Service
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

@Service
class BarService : BaseService<Bar, UUID, BarRepository>() {


    fun findAllTiposComida(): List<String> = this.repository.findTiposComida()

    fun findAbiertos(): List<Bar> {
        var bares: MutableList<Bar> = ArrayList()
        var now = LocalTime.now()
        for (bar in this.repository.findAll()) {
            if (bar.horaApertura.isBefore(now) && bar.horaCierre.isAfter(now)) {
                bares.add(bar)
            }
        }

        return bares
    }

    fun consultarHorario(id: UUID, pedidos: List<Pedido>): List<LocalTime> {
        var bar: Bar = this.findById(id).get()
        var horas: MutableList<LocalTime> = mutableListOf()
        horas.addAll(bar.horasDisponibles!!)

        for (h in bar.horasDisponibles!!) {
            for (p in pedidos) {
                if (p.horaRecogida == h || h.isBefore(LocalTime.now().plusMinutes(bar.tiempoPedido))) {
                    horas.remove(h)
                }
            }
        }

        bar.horasDisponibles = horas
        this.save(bar)

        return horas
    }

    fun calcularHorarios(bar: Bar) {
        var hourMin: LocalTime? = null
        var horas: MutableList<LocalTime>? = null
        horas = java.util.ArrayList()
        if (hourMin == null) {
            hourMin = bar.horaApertura
        }
        while (hourMin?.isBefore(bar.horaCierre)!!) {
            if (hourMin.isBefore(bar.horaCierre.minusMinutes(bar.tiempoPedido))) {
                hourMin = hourMin.plusMinutes(bar.tiempoPedido)
                horas.add(hourMin)
            } else {
                hourMin = bar.horaCierre
            }
        }

        bar.horasDisponibles = horas
        repository.save(bar)
        hourMin = null
        println("horas disponibles: ${bar.horasDisponibles}")
    }


    fun cargarHorarios() {
        for (bar in this.findAll()) {
            calcularHorarios(bar)
        }
    }

    fun editarBar(user: User, editarBarDTO: EditarBarDTO): Bar {
        var bar: Bar = user.bar!!
        with(editarBarDTO) {
            bar.nombre = nombre
            bar.tipoComida = tipoComida
            bar.horaApertura = horaApertura
            bar.horaCierre = horaCierre
            bar.tiempoPedido = tiempoPedido
        }

        this.repository.save(bar)
        calcularHorarios(bar)

        return bar
    }

}
