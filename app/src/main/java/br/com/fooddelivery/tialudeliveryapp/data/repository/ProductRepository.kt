package br.com.fooddelivery.tialudeliveryapp.data.repository

import br.com.fooddelivery.tialudeliveryapp.data.model.Product


interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(id: String): Product?
}

