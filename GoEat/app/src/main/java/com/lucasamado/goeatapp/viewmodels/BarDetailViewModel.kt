package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.models.bar.Bar
import com.lucasamado.goeatapp.models.bar.BarDetail
import com.lucasamado.goeatapp.repository.BarRepository
import com.lucasamado.goeatapp.repository.PlatoRepository
import javax.inject.Inject

class BarDetailViewModel @Inject constructor(
    barRepository: BarRepository
) : ViewModel() {

    val repo = barRepository

    fun getDetailBar(id: String): MutableLiveData<BarDetail> {
        return repo.getBar(id)
    }

}