package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.models.bar.BarDetailDto
import com.lucasamado.goeatapp.models.bar.BarDto
import com.lucasamado.goeatapp.repository.BarRepository
import javax.inject.Inject

class BarViewModel @Inject constructor(
        barRepository: BarRepository
    ): ViewModel() {

        val repo = barRepository

        fun getBares(): MutableLiveData<List<BarDto>>{
            return repo.getBaresList()
        }
}