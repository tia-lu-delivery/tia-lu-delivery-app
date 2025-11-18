package br.com.fooddelivery.tialudeliveryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope

import  br.com.fooddelivery.tialudeliveryapp.model.Order
import  br.com.fooddelivery.tialudeliveryapp.model.OrderStatus
import br.com.fooddelivery.tialudeliveryapp.data.repository.OrderRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import kotlin.String


class OrderDetailsViewModel(
    private val repository: OrderRepository = OrderRepository()
) : ViewModel() {

    private val _order = MutableStateFlow<Order?>(null)
    val order: StateFlow<Order?> = _order.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val defaultValue = Order(
        orderNumber = "0",
        openingTime = "0",
        status = OrderStatus.ACEITO,
        customerName = "N/A",
        customerPhone = "N/A",
        deliveryAddress = "N/A",
        items = mutableListOf()
    )

    fun loadOrder(orderId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val order = repository.getOrderById(orderId)
                _order.value = order ?: defaultValue
            } catch (e: Exception) {
                e.printStackTrace()
                _order.value = defaultValue
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun moveForwardStatus() {
        val currentOrder = _order.value ?: return

        val proximoStatus = when (currentOrder.status) {
            OrderStatus.ABERTO -> OrderStatus.ACEITO
            OrderStatus.ACEITO -> OrderStatus.FAZENDO
            OrderStatus.FAZENDO -> OrderStatus.FEITO
            OrderStatus.FEITO -> OrderStatus.SAIU_PARA_ENTREGA
            OrderStatus.SAIU_PARA_ENTREGA -> OrderStatus.ENTREGUE
            OrderStatus.ENTREGUE -> OrderStatus.ENTREGUE
        }

        val updatedOrder = currentOrder.copy(status = proximoStatus)
        _order.value = updatedOrder

        // viewModelScope.launch { repository.updateOrder(updatedOrder) } para implementação futura
    }
    fun getTextButton(): String{
        val currentStatus = _order.value?.status

        return when (currentStatus){
            OrderStatus.ABERTO -> "Aceitar Pedido"
            OrderStatus.ACEITO -> "Iniciar Preparo"
            OrderStatus.FAZENDO -> "Marcar Como Feito"
            OrderStatus.FEITO -> "Sair para entrega"
            OrderStatus.SAIU_PARA_ENTREGA -> "Marcar como entregue"
            OrderStatus.ENTREGUE -> "Pedido Entregue"
            null -> "..."
        }
    }
}