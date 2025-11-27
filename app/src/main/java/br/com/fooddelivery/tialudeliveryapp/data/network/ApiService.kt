package br.com.fooddelivery.tialudeliveryapp.data.network

import br.com.fooddelivery.tialudeliveryapp.model.Order
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("user/orders")
    suspend fun getOrders(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): OrdersResponse

}

data class OrdersResponse(
    val page: Int,
    val size: Int,
    val total_pedidos: Int,
    val pedidos: List<Order>
)