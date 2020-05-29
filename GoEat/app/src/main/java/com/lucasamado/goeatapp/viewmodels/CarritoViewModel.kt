package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.models.pedido.CreatePedido
import com.lucasamado.goeatapp.models.pedido.PedidoDto
import com.lucasamado.goeatapp.repository.BarRepository
import com.lucasamado.goeatapp.repository.PedidoRepository
import java.time.LocalTime
import javax.inject.Inject

class CarritoViewModel @Inject constructor(
    pedidoRepository: PedidoRepository,
    barRepository: BarRepository
) : ViewModel() {
    var pedidoRepo = pedidoRepository
    var barRepo = barRepository

    fun totalCarrito(): MutableLiveData<Double> = pedidoRepo.calcularTotalCarrito()

    fun horasRecogida(id: String): MutableLiveData<List<String>> = barRepo.consultarHorasRecogida(id)

    fun pagarPedido(createPedido: CreatePedido): MutableLiveData<PedidoDto> = pedidoRepo.pagar(createPedido)

}