package br.com.fooddelivery.tialudeliveryapp.data.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface de comunicação com o backend para pedidos.
 * Endpoint: GET /orders/list?status=
 *
 * O parâmetro `status` agora é opcional (String?) para permitir consultar
 * todos os pedidos quando null.
 */
interface OrdersApi {

    @GET("orders/list")
    suspend fun getOrdersByStatus(
        @Query("status") status: String?
    ): List<OrderDto>
}
