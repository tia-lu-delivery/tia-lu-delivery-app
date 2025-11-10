package br.com.fooddelivery.tialudeliveryapp.model

data class OrderPresentation(
    val id: String,
    val userName: String,
    val openedAtFormatted: String,
    val status: OrderStatus,
    val statusLabel: String
)
