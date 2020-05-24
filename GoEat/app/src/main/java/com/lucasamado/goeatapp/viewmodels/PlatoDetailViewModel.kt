package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.models.bar.Plato
import com.lucasamado.goeatapp.repository.PlatoRepository
import javax.inject.Inject

class PlatoDetailViewModel @Inject constructor(
    platoRepository: PlatoRepository
) : ViewModel() {

    val repo = platoRepository

    fun getPlato(id: String): MutableLiveData<Plato> {
        return repo.getPlato(id)
    }

}