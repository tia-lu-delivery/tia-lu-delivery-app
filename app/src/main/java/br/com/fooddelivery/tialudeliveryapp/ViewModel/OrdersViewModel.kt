package br.com.fooddelivery.tialudeliveryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fooddelivery.tialudeliveryapp.data.model.Order
import br.com.fooddelivery.tialudeliveryapp.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class OrdersViewModel : ViewModel() {

    private val _orders = MutableStateFlow<List<OrdersViewModel>>(emptyList())
    val orders: StateFlow<List<OrdersViewModel>> get() = _orders

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun getOrders(status: String) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getOrdersByStatus(status).awaitResponse()
                _isLoading.value = false

                if (response.isSuccessful) {
                    _orders.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Erro ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = "Falha: ${e.localizedMessage ?: "Erro desconhecido"}"
            }
        }
    }
}