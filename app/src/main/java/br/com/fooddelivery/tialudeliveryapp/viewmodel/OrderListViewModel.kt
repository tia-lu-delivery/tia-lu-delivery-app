package br.com.fooddelivery.tialudeliveryapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import  br.com.fooddelivery.tialudeliveryapp.model.Order
import  br.com.fooddelivery.tialudeliveryapp.model.OrderStatus
import br.com.fooddelivery.tialudeliveryapp.data.repository.OrderRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import kotlin.String

data class OrderListUiState(
    val isLoading: Boolean = false,
    val orders: List<Order> = emptyList(),
    val currentPage: Int = 1,
    val pageSize: Int = 5,
    val totalOrders: Int = 0,
    val maxPage: Int = 1,
    val errorMessage: String? = null
)

class OrderListViewModel(
    private val repository: OrderRepository = OrderRepository()
) : ViewModel() {

    private val _uiState = MutableLiveData(OrderListUiState())
    val uiState: LiveData<OrderListUiState> = _uiState

    init {
        loadOrders()
    }

    fun loadOrders(page: Int = 1){
        viewModelScope.launch {
            try {
                updateState { it.copy(isLoading = true) }
                val allOrders = repository.getAllOrders()
                val sorted = allOrders.sortedByDescending { it.openingTime }
                applyPagination(sorted, page)
            }catch (e: Exception){
                val msg = if (e.message?.contains("401") == true)
                    "Não autorizado. Faça login novamente."
                else
                    "Erro ao carregar pedidos."

                updateState { it.copy(isLoading = false, errorMessage = msg) }
            }
        }
    }

    fun nextPage() {
        val state = _uiState.value ?: return
        if (state.currentPage < state.maxPage) {
            loadOrders(state.currentPage + 1)
        }
    }

    fun previousPage() {
        val state = _uiState.value ?: return
        if (state.currentPage > 1) {
            loadOrders(state.currentPage - 1)
        }
    }

    private fun applyPagination(all: List<Order>, page: Int){
        val state = _uiState.value ?: return
        val pageSize = state.pageSize
        val total = all.size
        val maxPage = if (total == 0) 1 else (total + pageSize - 1) / pageSize
        val from = (page - 1) * pageSize
        val to = (from + pageSize).coerceAtMost(total)
        val paginated =
            if (from < total) all.subList(from, to) else emptyList()


        updateState {
            it.copy(
                isLoading = false,
                orders = paginated,
                currentPage = page,
                totalOrders = total,
                maxPage = maxPage,
                errorMessage = null
            )
        }
    }

    private fun updateState(transform: (OrderListUiState) -> OrderListUiState){
        val current = _uiState.value ?: OrderListUiState()
        _uiState.value = transform(current)
    }
}