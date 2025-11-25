package br.com.fooddelivery.tialudeliveryapp.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface OrdersApi {
    @GET("orders/list")
    suspend fun getOrdersByStatus(
        @Query("status") status: String?
    ): List<OrderDto>
}