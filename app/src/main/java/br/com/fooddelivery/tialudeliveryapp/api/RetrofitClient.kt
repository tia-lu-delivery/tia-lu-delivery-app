package br.com.fooddelivery.tialudeliveryapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val api: PedidoApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://localhost/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PedidoApi::class.java)
    }
}

