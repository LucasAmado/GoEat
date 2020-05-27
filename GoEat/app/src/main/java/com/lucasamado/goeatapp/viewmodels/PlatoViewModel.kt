package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.models.plato.Plato
import com.lucasamado.goeatapp.repository.PlatoRepository
import javax.inject.Inject

class PlatoViewModel @Inject constructor(
    platoRepository: PlatoRepository
) : ViewModel() {

    val repo = platoRepository

    fun getListaPlatosByTipo(tipo: String, id: String): MutableLiveData<List<Plato>> {
        return repo.getPlatosByTipo(tipo, id)
    }
}