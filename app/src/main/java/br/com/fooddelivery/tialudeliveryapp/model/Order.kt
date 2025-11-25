package br.com.fooddelivery.tialudeliveryapp.model

data class Order(
    val id: String,
    val customerName: String,
    val openedAt: String, // ISO 8601 expected
    val totalPrice: Double?,
    val items: List<OrderItem> = emptyList(),
    val status: OrderStatus
)
