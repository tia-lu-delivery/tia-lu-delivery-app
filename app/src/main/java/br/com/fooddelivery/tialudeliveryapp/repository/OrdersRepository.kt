package br.com.fooddelivery.tialudeliveryapp.data.repository

import br.com.fooddelivery.tialudeliveryapp.model.Order
import br.com.fooddelivery.tialudeliveryapp.model.OrderStatus

interface OrdersRepository {
    suspend fun fetchOrders(): List<Order>
    suspend fun fetchOrdersByStatus(status: OrderStatus?): List<Order>
}
