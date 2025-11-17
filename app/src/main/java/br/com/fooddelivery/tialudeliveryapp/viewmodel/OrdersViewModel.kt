import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fooddelivery.tialudeliveryapp.data.repository.OrdersRepository
import br.com.fooddelivery.tialudeliveryapp.model.Order
import br.com.fooddelivery.tialudeliveryapp.model.OrderStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class OrdersViewModel(
    private val repository: OrdersRepository
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // 🔹 Carrega todos os pedidos
    fun loadOrders() {
        viewModelScope.launch {
            try {
                _loading.value = true
                val result = repository.fetchOrders()
                _orders.value = result
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao carregar pedidos"
            } finally {
                _loading.value = false
            }
        }
    }

    // 🔹 Carrega pedidos filtrados por status
    fun loadOrdersByStatus(status: OrderStatus) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val result = repository.fetchOrdersByStatus(status)
                _orders.value = result
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao filtrar pedidos"
            } finally {
                _loading.value = false
            }
        }
    }
}
