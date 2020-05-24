package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.models.bar.BarDto
import com.lucasamado.goeatapp.repository.BarRepository
import com.lucasamado.goeatapp.repository.PlatoRepository
import javax.inject.Inject

class BarDetailViewModel @Inject constructor(
    barRepository: BarRepository,
    platoRepository: PlatoRepository
) : ViewModel() {

    val barRepo = barRepository
    val platosRepo = platoRepository

    fun getDetailBar(id: String): MutableLiveData<BarDto> {
        return barRepo.getBar(id)
    }

    fun getTipos(id: String): MutableLiveData<List<String>> = platosRepo.findTiposByBar(id)

}