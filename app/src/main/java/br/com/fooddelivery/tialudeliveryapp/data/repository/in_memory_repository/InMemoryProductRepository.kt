package br.com.fooddelivery.tialudeliveryapp.data.repository.in_memory_repository

import br.com.fooddelivery.tialudeliveryapp.data.mock.products
import br.com.fooddelivery.tialudeliveryapp.data.model.Product


class InMemoryProductRepository {
    fun getProductById(id: String): Product? {
        return products.find { it.productId == id }
    }
}