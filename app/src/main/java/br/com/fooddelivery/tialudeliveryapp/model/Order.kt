package br.com.fooddelivery.tialudeliveryapp.model

data class Order(
    val id: String,
    val userName: String,
    val openedAt: String, // This expect values in the ISO 8601 format
    val status: OrderStatus
)
