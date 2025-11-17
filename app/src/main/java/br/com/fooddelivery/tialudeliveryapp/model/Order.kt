package br.com.fooddelivery.tialudeliveryapp.model

data class OrderItem(
    val id: String,
    val name: String,
    val quantity: Int,
    val price: Double
)

enum class OrderStatus {
    ABERTO,
    ACEITO,
    FAZENDO,
    FEITO,
    SAIU_PARA_ENTREGA,
    ENTREGUE,

}

data class Order(
    val id: String,
    val userName: String,
    val openedAt: String,
    val status: OrderStatus,
    val customerName: String,
    val items: List<OrderItem>,
    val totalPrice: Double
)