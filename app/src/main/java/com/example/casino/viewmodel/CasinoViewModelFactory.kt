package com.example.casino.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.casino.data.repository.AccountRepository

class CasinoViewModelFactory(private val accountRepository: AccountRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CasinoViewModel::class.java)) {
            CasinoViewModel(accountRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
