package br.com.fooddelivery.tialudeliveryapp.api

import br.com.fooddelivery.tialudeliveryapp.model.Establishment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MerchantApiService {

    @POST("merchante/create")
    suspend fun createMerchant(@Body establishment: Establishment): Response<Unit>
}