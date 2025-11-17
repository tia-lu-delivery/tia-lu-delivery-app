interface MenuApi{
    @POST("menu/category")
    suspend fun createCategory(
        @Body body: CategoryRequest
    ): CategoryResponse
}