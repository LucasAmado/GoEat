package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.repository.PedidoRepository
import javax.inject.Inject

class CarritoViewModel @Inject constructor(
    pedidoRepository: PedidoRepository
) : ViewModel() {
    var repo = pedidoRepository

    fun totalCarrito(): MutableLiveData<Double> = repo.calcularTotalCarrito()

}