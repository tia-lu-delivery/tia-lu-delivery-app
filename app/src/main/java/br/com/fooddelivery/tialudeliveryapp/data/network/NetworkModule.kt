package br.com.fooddelivery.tialudeliveryapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    val ordersApi: OrdersApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.tialu.com.br/") // substitua pela URL real
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OrdersApi::class.java)
    }
}