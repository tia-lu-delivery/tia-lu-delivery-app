package br.com.fooddelivery.tialudeliveryapp.data.model

data class Product(
    val productId: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val quantity: Int,
    val isAvailable: Boolean
)