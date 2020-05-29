package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.models.pedido.PedidoDetalleDto
import com.lucasamado.goeatapp.repository.PedidoRepository
import javax.inject.Inject

class PedidoDetalleViewModel @Inject constructor(
    pedidoRepository: PedidoRepository
) : ViewModel() {
    var repo = pedidoRepository

    fun getPedido(id: String): MutableLiveData<PedidoDetalleDto> = repo.getPedidoDetalle(id)

    fun changePedidoFav(id: String): MutableLiveData<Boolean> = repo.changePedidoFavorito(id)

}