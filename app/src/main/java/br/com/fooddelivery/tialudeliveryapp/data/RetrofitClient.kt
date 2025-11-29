package br.com.fooddelivery.tialudeliveryapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://tialu-api.com.br/"


object RetrofitClient {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: PaymentApiService by lazy {
        retrofit.create(PaymentApiService::class.java)
    }
}
