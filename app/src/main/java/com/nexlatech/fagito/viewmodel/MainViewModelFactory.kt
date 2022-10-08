package com.nexlatech.fagito.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nexlatech.fagito.repository.FagitoRepository

class MainViewModelFactory(private val repository: FagitoRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}