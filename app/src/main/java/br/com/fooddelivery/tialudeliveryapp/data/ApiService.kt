package br.com.fooddelivery.tialudeliveryapp.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentApiService {

    @POST("api/v1/user/{idUsuario}/payment-methods")
    suspend fun savePaymentMethod(
        @Path("idUsuario") userId: String,
        @Body request: PaymentRequest
    ): Response<PaymentResponse>
}