import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//basic class orders
data class Order (
    val id: String,
    val userName: String,
    val openedAt: String,
    val status: OrderStatus
)


//enum constants
enum class OrderStatus {
    AGUARDANDO_APROVACAO,
    ACEITO,
    FAZENDO,
    FEITO,
    ESPERANDO_ENTREGADOR,
    SAIU_PARA_ENTREGA,
    ENTREGUE,
    CANCELADO,
    REJEITADO
}


//data class with formated data
data class OrderPresentation(
    val id: String,
    val userName: String,
    val openedAtFormatted: String,
    val status: OrderStatus,
    val statusLabel: String
)


//UI state
data class OrdersUiState (
    val orders: List<OrderPresentation> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val activeFilter: OrderStatus? = null
)


//Repository
interface OrdersRepository {
    suspend fun fetchOrders(): List<Order>
}


//ViewModel
class OrdersViewModel(
    val repository: OrdersRepository
) : ViewModel() {


    //States
    private val _allOrders = MutableStateFlow<List<Order>>(emptyList())
    private val _filter = MutableStateFlow<OrderStatus?>(null)
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)


    //StateFlow for UI consume
    @RequiresApi(Build.VERSION_CODES.O)
    val uiState: StateFlow<OrdersUiState> = combine(
        _allOrders, _filter, _isLoading, _error
    ) { all, filter, loading, error ->
        val filtered = applyFilterAndSort(all, filter)
        OrdersUiState(
            orders = filtered.map { toPresentation(it) },
            isLoading = loading,
            errorMessage = error,
            activeFilter = filter
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, OrdersUiState())


    init {
        viewModelScope.launch {
            refresh()
        }
    }


    //Filters Set
    fun setFilter(status: OrderStatus?) {
        _filter.value = status
    }


    // Force Refresh for Orders
    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val list = repository.fetchOrders()
                _allOrders.value = list
            } catch (t: Throwable) {
                _error.value = t.localizedMessage ?: "Erro ao carregar pedidos"
            } finally {
                _isLoading.value = false
            }
        }
    }


    // Refresh
    fun retry() = refresh()


    // Filter Status (filter == null return all orders) ordered by openedAt desc
    @RequiresApi(Build.VERSION_CODES.O)
    private fun applyFilterAndSort(all: List<Order>, filter: OrderStatus?): List<Order> {
        val filtered = if (filter == null) all else all.filter { it.status == filter }
        return filtered.sortedByDescending { parseOpenedAtToEpochMillis(it.openedAt) }
    }


    // Convert Order -> OrderPresentation with formated data and label of status
    @RequiresApi(Build.VERSION_CODES.O)
    private fun toPresentation(order: Order): OrderPresentation {
        return OrderPresentation(
            id = order.id,
            userName = order.userName,
            openedAtFormatted = formatOpenedAt(order.openedAt),
            status = order.status,
            statusLabel = order.status.toLabel()
        )
    }


    // Format ISO -> "dd/MM/yyyy HH:mm"
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatOpenedAt(iso: String): String {
        return try {
            val odt = OffsetDateTime.parse(iso)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault())
            formatter.format(odt)
        } catch (t: Throwable) {
            iso
        }
    }


    // Parse to ordernation
    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseOpenedAtToEpochMillis(iso: String): Long {
        return try {
            val odt = OffsetDateTime.parse(iso)
            odt.toInstant().toEpochMilli()
        } catch (t: Throwable) {
            0L
        }
    }
}


// label of status with status formated
private fun OrderStatus.toLabel(): String = when (this) {
    OrderStatus.AGUARDANDO_APROVACAO -> "Aguardando aprovação"
    OrderStatus.ACEITO -> "Aceito"
    OrderStatus.FAZENDO -> "Fazendo"
    OrderStatus.FEITO -> "Feito"
    OrderStatus.ESPERANDO_ENTREGADOR -> "Esperando entregador"
    OrderStatus.SAIU_PARA_ENTREGA -> "Saiu para entrega"
    OrderStatus.ENTREGUE -> "Entregue"
    OrderStatus.CANCELADO -> "Cancelado"
    OrderStatus.REJEITADO -> "Rejeitado"
}
