package com.lucasamado.goeatapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasamado.goeatapp.models.Bar
import com.lucasamado.goeatapp.repository.BarRepository
import com.lucasamado.goeatapp.repository.UserRepository
import javax.inject.Inject

class BarViewModel @Inject constructor(
    barRepository: BarRepository
): ViewModel() {

    val repo = barRepository

    fun getBares(): MutableLiveData<List<Bar>>{
        return repo.getBares()
    }
}