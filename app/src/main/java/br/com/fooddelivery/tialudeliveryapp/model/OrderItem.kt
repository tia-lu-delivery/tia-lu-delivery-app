package br.com.fooddelivery.tialudeliveryapp.model

data class OrderItem(
    val id: String,
    val name: String,
    val quantity: Int,
    val price: Double
)
