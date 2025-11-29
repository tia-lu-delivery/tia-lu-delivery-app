package br.com.fooddelivery.tialudeliveryapp.model

data class OrderPresentation(
    val id: String,
    val customerName: String,
    val openedAtFormatted: String,
    val status: OrderStatus,
    val statusLabel: String
)
