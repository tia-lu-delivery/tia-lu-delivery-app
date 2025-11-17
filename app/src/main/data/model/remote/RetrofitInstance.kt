object RetrofitInstance{
    val api: MenuApi by lazy {
        Retrofit.Builder()
            .baseURL("https://projeto.api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MenuApi::class.java)
    }
}