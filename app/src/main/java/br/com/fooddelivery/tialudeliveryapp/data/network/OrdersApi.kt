package br.com.fooddelivery.tialudeliveryapp.data.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface de comunicação com o backend para pedidos.
 * Endpoint: GET /orders/list?status=
 * O parâmetro `status` aceita variações em português e inglês, com ou sem acento:
 * - "aberto", "Aberto", "open"
 * - "em preparo", "preparando", "preparing"
 * - "entregue", "Entregue", "delivered"
 */
interface OrdersApi {
    @GET("orders/list")
    suspend fun getOrdersByStatus(@Query("status") status: String): List<OrderDto>
}