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
    ESPERANDO_ENTREGADOR,
    SAIU_PARA_ENTREGA,
    ENTREGUE,
}
data class Order(
    val orderNumber: String,
    val openingTime: String,
    val status: OrderStatus,
    val customerName: String,
    val customerPhone: String,
    val deliveryAddress: String,
    val items: MutableList<OrderItem>
)
