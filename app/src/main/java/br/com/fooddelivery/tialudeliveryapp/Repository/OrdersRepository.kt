package br.com.fooddelivery.tialudeliveryapp.data.repository

import br.com.fooddelivery.tialudeliveryapp.domain.Order
import br.com.fooddelivery.tialudeliveryapp.domain.OrderStatus

interface OrdersRepository {
    suspend fun fetchOrders(): List<Order>
    suspend fun fetchOrdersByStatus(status: OrderStatus?): List<Order>
}