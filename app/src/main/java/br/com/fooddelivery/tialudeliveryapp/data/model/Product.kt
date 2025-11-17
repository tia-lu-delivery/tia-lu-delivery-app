package br.com.fooddelivery.tialudeliveryapp.data.model

data class Product(
    val name: String,
    val price: Double,
    val descripton: String,
    val quantity: Int,
    val imageUrl: String,
    val isAvailable: Boolean
)