package br.com.fooddelivery.tialudeliveryapp.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fooddelivery.tialudeliveryapp.OrdersRepository
import br.com.fooddelivery.tialudeliveryapp.model.OrderPresentation
import br.com.fooddelivery.tialudeliveryapp.model.OrderStatus
import br.com.fooddelivery.tialudeliveryapp.model.toLabelPt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.collections.map

data class OrdersUiState(
    val orders: List<OrderPresentation> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val activeFilter: OrderStatus? = null
)

class OrdersViewModel(
    private val repository: OrdersRepository
) : ViewModel() {

    private val _allOrders = MutableStateFlow<List<br.com.fooddelivery.tialudeliveryapp.model.Order>>(emptyList())
    private val _filter = MutableStateFlow<OrderStatus?>(null)
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    val availableFilters: List<Pair<OrderStatus?, String>> = listOf(
        null to "Todos"
    ) + OrderStatus.values().map { it to it.toLabelPt() }

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
        refresh()
    }

    fun setFilter(status: OrderStatus?) {
        _filter.value = status
        refresh(status)
    }

    fun retry() = refresh(_filter.value)

    fun refresh(status: OrderStatus? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val list = repository.fetchOrdersByStatus(status)
                _allOrders.value = list
            } catch (t: Throwable) {
                _error.value = t.localizedMessage ?: "Erro ao carregar pedidos"
            } finally {
                _isLoading.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun applyFilterAndSort(all: List<br.com.fooddelivery.tialudeliveryapp.model.Order>, filter: OrderStatus?): List<br.com.fooddelivery.tialudeliveryapp.model.Order> {
        val filtered = if (filter == null) all else all.filter { it.status == filter }
        return filtered.sortedByDescending { parseOpenedAtToEpochMillis(it.openedAt) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun toPresentation(order: br.com.fooddelivery.tialudeliveryapp.model.Order): OrderPresentation {

        return OrderPresentation(
            id = order.id,
            userName = order.userName,
            openedAtFormatted = formatOpenedAt(order.openedAt),
            status = order.status,
            statusLabel = order.status.toLabelPt()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatOpenedAt(iso: String): String {
        return try {
            val odt = OffsetDateTime.parse(iso)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault())
            formatter.format(odt)
        } catch (t: Throwable) {
            iso
        }
    }

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
