package br.com.fooddelivery.tialudeliveryapp.network

import PedidoRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.tialudelivery.com.br/api/v1/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val pedidoApi: PedidoApi by lazy {
        retrofit.create(PedidoApi::class.java)
    }

    val pedidoRepository: PedidoRepository by lazy {
        PedidoRepository(pedidoApi)
    }
}