package br.com.fooddelivery.tialudeliveryapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val api: OrdersApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://seu_backend.com/") // coloque sua URL real
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OrdersApi::class.java)
    }
}
