package br.com.fooddelivery.tialudeliveryapp.data.repository.ktor_repository

import br.com.fooddelivery.tialudeliveryapp.network.KtorHttpClient
import br.com.fooddelivery.tialudeliveryapp.network.model.ProductDetailRequest
import br.com.fooddelivery.tialudeliveryapp.network.model.ProductDetailResponse

class KtorProductRepository {
    suspend fun getProductById(id: String): Result<ProductDetailResponse> {
        val product = KtorHttpClient.getProductDetail(data = ProductDetailRequest(productId = id))

        return product
    }
}