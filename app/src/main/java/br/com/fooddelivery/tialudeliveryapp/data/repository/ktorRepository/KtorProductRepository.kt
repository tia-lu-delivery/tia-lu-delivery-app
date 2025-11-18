package br.com.fooddelivery.tialudeliveryapp.data.repository.ktorRepository

import br.com.fooddelivery.tialudeliveryapp.data.model.Product
import br.com.fooddelivery.tialudeliveryapp.data.repository.ProductRepository
import br.com.fooddelivery.tialudeliveryapp.network.KtorHttpClient
import br.com.fooddelivery.tialudeliveryapp.network.model.ProductDetailRequest

class KtorProductRepository: ProductRepository {
    override suspend fun getProducts(): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(id: String): Product? {
        val product = KtorHttpClient.getProductDetail(data = ProductDetailRequest(productId = id))

        return product
    }

}