package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.repository.PedidoRepository
import com.lucasamado.goeatapp.repository.PlatoRepository
import javax.inject.Inject

class ListaPlatosViewModel @Inject constructor(
    pedidoRepository: PedidoRepository
) : ViewModel() {
    var repo = pedidoRepository

    fun tamanyoCarrito(): MutableLiveData<Int> = repo.consultarTamanyoCarrito()
}