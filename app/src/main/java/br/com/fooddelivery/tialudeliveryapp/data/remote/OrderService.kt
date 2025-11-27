package br.com.fooddelivery.tialudeliveryapp.data.remote

import br.com.fooddelivery.tialudeliveryapp.data.model.PendingOrder
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OrderService {
    // GET /orders/list?status=Pending
    @GET("/orders/list")
    suspend fun getOrdersByStatus(
        @Query("status") status: String = "Pending"
    ): Response<List<PendingOrder>>
}

