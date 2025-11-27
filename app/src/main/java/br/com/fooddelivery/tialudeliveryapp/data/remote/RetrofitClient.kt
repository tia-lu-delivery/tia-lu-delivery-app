package br.com.fooddelivery.tialudeliveryapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://seu-servidor.com/api/"
    // substituir pelo domínio ou IP do backend (precisa terminar com "/")

    val orderService: OrderService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OrderService::class.java)
    }
}
