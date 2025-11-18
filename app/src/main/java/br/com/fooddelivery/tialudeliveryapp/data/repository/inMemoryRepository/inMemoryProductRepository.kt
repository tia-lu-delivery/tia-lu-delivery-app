package br.com.fooddelivery.tialudeliveryapp.data.repository.inMemoryRepository

import br.com.fooddelivery.tialudeliveryapp.data.mock.products
import br.com.fooddelivery.tialudeliveryapp.data.model.Product
import br.com.fooddelivery.tialudeliveryapp.data.repository.ProductRepository

class inMemoryProductRepository: ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return products
    }

    override suspend fun getProductById(id: String): Product? {
        val product = products.find { it.productId == id }

        return product
    }
}