package br.com.fooddelivery.tialudeliveryapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fooddelivery.tialudeliveryapp.data.model.PaymentMethod
import br.com.fooddelivery.tialudeliveryapp.data.repository.FakePaymentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Define os estados da tela
sealed class PaymentMethodsUiState {
    object Loading : PaymentMethodsUiState()
    data class Success(val methods: List<PaymentMethod>) : PaymentMethodsUiState()
    data class Error(val message: String) : PaymentMethodsUiState()
}

class PaymentMethodsViewModel : ViewModel() {
    // Instancia o repositório de teste
    private val repository = FakePaymentRepository()

    private val _uiState = MutableStateFlow<PaymentMethodsUiState>(PaymentMethodsUiState.Loading)
    val uiState: StateFlow<PaymentMethodsUiState> = _uiState.asStateFlow()

    init {
        fetchPaymentMethods()
    }
    fun fetchPaymentMethods() {
        viewModelScope.launch {
            _uiState.value = PaymentMethodsUiState.Loading
            try {
                // Chama a função do repositório para obter os meios de pagamento
                val result = repository.getPaymentMethods()
                _uiState.value = PaymentMethodsUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = PaymentMethodsUiState.Error("Erro ao carregar meios de pagamento.")
            }
        }
    }
}
