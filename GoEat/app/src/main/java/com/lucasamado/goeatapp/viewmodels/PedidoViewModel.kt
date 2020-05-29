package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.models.pedido.PedidoDto
import com.lucasamado.goeatapp.repository.PedidoRepository
import javax.inject.Inject

class PedidoViewModel @Inject constructor(
    pedidoRepository: PedidoRepository
) : ViewModel() {
    var repo = pedidoRepository

    fun loadMisPedidos(): MutableLiveData<List<PedidoDto>> = repo.loadMisPedidos()
}