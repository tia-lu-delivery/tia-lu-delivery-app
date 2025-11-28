package br.com.fooddelivery.tialudeliveryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import br.com.fooddelivery.tialudeliveryapp.api.RetrofitInstance
import kotlinx.coroutines.launch

class MerchantViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    fun createMerchant(establishment: Establishment) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.createMerchant(establishment)
                if (response.isSuccessful) {
                    _status.value = "Cadastro realizado com sucesso!"
                } else {
                    _status.value = "Falha ao cadastrar: ${response.code()}"
                }
            } catch (e: Exception) {
                _status.value = "Erro: ${e.message}"
            }
        }
    }
}