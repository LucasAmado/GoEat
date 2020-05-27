package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.models.pedido.LineaPedidoDto
import com.lucasamado.goeatapp.models.plato.Plato
import com.lucasamado.goeatapp.models.plato.PlatoDto
import com.lucasamado.goeatapp.repository.PedidoRepository
import com.lucasamado.goeatapp.repository.PlatoRepository
import java.util.*
import javax.inject.Inject

class PlatoDetailViewModel @Inject constructor(
    platoRepository: PlatoRepository,
    pedidoRepository: PedidoRepository
) : ViewModel() {

    val platoRepo = platoRepository
    val pedidoRepo = pedidoRepository

    fun getPlato(id: String): MutableLiveData<PlatoDto> {
        return platoRepo.getPlato(id)
    }

    fun actualizarCarrito(cantidad: Int, id: String): MutableLiveData<LineaPedidoDto>{
        return pedidoRepo.actualizarCarrito(cantidad, id)
    }

    fun tamanyoCarrito(): MutableLiveData<Int> = pedidoRepo.consultarTamanyoCarrito()

    fun consultarBarCarrito(): MutableLiveData<PlatoDto> = platoRepo.consultarBarCarrito()

    fun deletePlato(id: String): MutableLiveData<Boolean> = pedidoRepo.deletePlato(id)

}