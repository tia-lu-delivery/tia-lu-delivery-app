package br.com.fooddelivery.tialudeliveryapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fooddelivery.tialudeliveryapp.data.model.PendingOrder
import br.com.fooddelivery.tialudeliveryapp.data.repository.OrdersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Mostra o estado atual da tela (Se está carregando, se está exibindo a lista, etc)
sealed class PendingOrdersUiState {
    object Loading : PendingOrdersUiState()
    data class Success(val orders: List<PendingOrder>) : PendingOrdersUiState()
    data class Error(val message: String) : PendingOrdersUiState()
}

class PendingOrdersViewModel : ViewModel() {
    private val repository = OrdersRepository()
    private val _uiState = MutableStateFlow<PendingOrdersUiState>(PendingOrdersUiState.Loading)
    val uiState: StateFlow<PendingOrdersUiState> = _uiState.asStateFlow()

    init {
        fetchPendingOrders()
    }

    /**
     * Busca os pedidos pendentes no Repository e atualiza o _uiState.
     */
    fun fetchPendingOrders() {
        _uiState.value = PendingOrdersUiState.Loading
        viewModelScope.launch {
            try {
                val orders = repository.getPendingOrders()
                _uiState.value = PendingOrdersUiState.Success(orders)
            } catch (e: Exception) {
                _uiState.value = PendingOrdersUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    /**
     * Chamado pelo botão 'Aceitar' da UI.
     */
    fun acceptOrder(orderId: String) {
        viewModelScope.launch {
            try {
                if (repository.acceptOrder(orderId)) {
                    fetchPendingOrders()
                }
            } catch (e: Exception) {
                println("Falha ao aceitar pedido: ${e.message}")
            }
        }
    }

    /**
     * Chamado pelo botão 'Rejeitar' da UI.
     */
    fun rejectOrder(orderId: String) {
        viewModelScope.launch {
            try {
                if (repository.rejectOrder(orderId)) {
                    fetchPendingOrders()
                }
            } catch (e: Exception) {
                // Retornando erro
                println("Falha ao rejeitar pedido: ${e.message}")
            }
        }
    }
}