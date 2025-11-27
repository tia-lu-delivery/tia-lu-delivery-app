object RetrofitClient {

    private const val BASE_URL = "https://api.tialudelivery.com.br/api/v1/"

    private val retrofit by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
    }

    val pedidoApi: PedidoApi by lazy {
        retrofit.create(PedidoApi::class.java)
    }

    val pedidoRepository: PedidoRepository by lazy {
        PedidoRepository(pedidoApi)
    }
}
