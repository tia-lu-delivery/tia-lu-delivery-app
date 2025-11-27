package br.com.fooddelivery.tialudeliveryapp.data.model

// Modelo de dados para um pedido pendente

data class PendingOrder(
    val id: String,
    val orderNumber: String,
    val customerName: String,
    val items: List<OrderItem>,
    val totalPrice: Double,
    val orderTime: String,
    val status: String = "Pending"
) {
    val itemCount: Int
        get() = items.sumOf { it.quantity }
}

// Modelo de dados para um item do pedido

data class OrderItem(
    val id: String,
    val name: String,
    val description: String,
    val quantity: Int,
    val price: Double,
    val imageUrl: String? = null
) {
    val subtotal: Double
        get() = quantity * price
}