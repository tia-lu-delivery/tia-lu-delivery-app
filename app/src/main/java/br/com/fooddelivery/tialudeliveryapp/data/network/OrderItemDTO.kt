package br.com.fooddelivery.tialudeliveryapp.data.network

data class OrderItemDto(
    val id: String,
    val name: String,
    val quantity: Int,
    val price: Double
)
