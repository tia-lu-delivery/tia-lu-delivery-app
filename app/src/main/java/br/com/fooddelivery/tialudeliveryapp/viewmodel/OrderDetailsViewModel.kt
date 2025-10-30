package br.com.fooddelivery.tialudeliveryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

import  br.com.fooddelivery.tialudeliveryapp.model.Order
import  br.com.fooddelivery.tialudeliveryapp.model.OrderStatus
import  br.com.fooddelivery.tialudeliveryapp.model.OrderItem

import kotlin.String

class OrderDetailsViewModel : ViewModel(){
    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    fun loadOrder() {
        val orderMock = Order( //Alterar para sincronizar com a api depois (Ian)
            orderNumber = "1023",
            openingTime = "11:50 11-10-2025",
            status = OrderStatus.ACEITO,
            customerName = "Maria",
            customerPhone = "(75) 99999-0000",
            deliveryAddress = "Rua Francisco, 123",
            items = mutableListOf(OrderItem("1", "Coca-cola", 2, 10.0),
                OrderItem("0", "Guaraná", 2, 10.0))
        )

        _order.value = orderMock
    }
    fun moveFowardStatus(){
        val currentOrder = _order.value ?: return

        val proximoStatus = when (currentOrder.status) {
            OrderStatus.ABERTO -> OrderStatus.ACEITO
            OrderStatus.ACEITO -> OrderStatus.FAZENDO
            OrderStatus.FAZENDO -> OrderStatus.ESPERANDO_ENTREGADOR
            OrderStatus.ESPERANDO_ENTREGADOR -> OrderStatus.SAIU_PARA_ENTREGA
            OrderStatus.SAIU_PARA_ENTREGA -> OrderStatus.ENTREGUE
            OrderStatus.ENTREGUE -> OrderStatus.ENTREGUE
        }
        val updatedOrder = currentOrder.copy(status = proximoStatus)

        _order.value = updatedOrder
    }
    fun getTextButton(): String{
        val currentStatus = _order.value?.status

        return when (currentStatus){
            OrderStatus.ABERTO -> "Aceitar Pedido"
            OrderStatus.ACEITO -> "Iniciar Preparo"
            OrderStatus.FAZENDO -> "Marcar Como Feito"
            OrderStatus.ESPERANDO_ENTREGADOR -> "Sair para entrega"
            OrderStatus.SAIU_PARA_ENTREGA -> "Marcar como entregue"
            OrderStatus.ENTREGUE -> "Pedido Entregue"
            null -> "..."
        }
    }
}