package br.com.fooddelivery.tialudeliveryapp.data.network

import br.com.fooddelivery.tialudeliveryapp.model.Order
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body

interface ApiService {

    @GET("order/{orderId}")
    suspend fun getOrder(
        @Path("orderId") orderId: String
    ): Order


    @POST("order/{orderId}")
    suspend fun updateOrder(
        @Path("orderId") orderId: String,
        @Body order: Order
    ): Order
}
