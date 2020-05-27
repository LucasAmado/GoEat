package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.models.pedido.LineaPedidoDto
import com.lucasamado.goeatapp.repository.PedidoRepository
import javax.inject.Inject

class LineaPedidoViewModel @Inject constructor(
    pedidoRepository: PedidoRepository
) : ViewModel() {
    var repo = pedidoRepository

    fun verCarrito(): MutableLiveData<List<LineaPedidoDto>> = repo.verCarrito()
}