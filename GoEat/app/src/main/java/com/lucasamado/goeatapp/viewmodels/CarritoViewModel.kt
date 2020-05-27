package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.repository.PedidoRepository
import javax.inject.Inject

class CarritoViewModel @Inject constructor(
    pedidoRepository: PedidoRepository
) : ViewModel() {
    var repo = pedidoRepository

    fun tamanyoCarrito(): MutableLiveData<Int> = repo.consultarTamanyoCarrito()

}