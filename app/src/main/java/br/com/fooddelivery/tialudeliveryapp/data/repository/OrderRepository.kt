package br.com.fooddelivery.tialudeliveryapp.data.repository

import br.com.fooddelivery.tialudeliveryapp.data.network.RetrofitClient
import br.com.fooddelivery.tialudeliveryapp.model.Order
import br.com.fooddelivery.tialudeliveryapp.model.OrderItem
import br.com.fooddelivery.tialudeliveryapp.model.OrderStatus


val mockData = mutableListOf(
    Order(
        orderNumber = "1020",
        openingTime = "11:50 11-10-2025",
        status = OrderStatus.ACEITO,
        customerName = "Maria",
        customerPhone = "(75) 99999-0000",
        deliveryAddress = "Rua Francisco, 123",
        items = mutableListOf(OrderItem("1", "Coca-cola Zero", 2, 10.0),
            OrderItem("0", "Guaraná", 2, 10.0))
    ),
    Order(
        orderNumber = "1021",
        openingTime = "11:50 11-10-2025",
        status = OrderStatus.FEITO,
        customerName = "Bia",
        customerPhone = "(75) 99999-0000",
        deliveryAddress = "Rua Francisco, 123",
        items = mutableListOf(OrderItem("1", "Pizza", 2, 10.0),
            OrderItem("0", "Guaraná", 2, 10.0))
    ),
    Order(
        orderNumber = "1022",
        openingTime = "11:50 11-10-2025",
        status = OrderStatus.SAIU_PARA_ENTREGA,
        customerName = "Valeria",
        customerPhone = "(75) 99999-0000",
        deliveryAddress = "Rua Francisco, 123",
        items = mutableListOf(OrderItem("1", "Macarronada", 2, 10.0),
            OrderItem("0", "Guaraná", 2, 10.0))
    ),
    Order(
        orderNumber = "1023",
        openingTime = "11:50 11-10-2025",
        status = OrderStatus.ABERTO,
        customerName = "Ian",
        customerPhone = "(75) 99999-0000",
        deliveryAddress = "Rua Francisco, 123",
        items = mutableListOf(OrderItem("1", "Coca-cola", 2, 10.0),
            OrderItem("0", "Guaraná", 2, 10.0))
    )
)

fun mockGetOrder(orderNumber: String): Order?{
    return mockData.find { it.orderNumber == orderNumber }
}

class OrderRepository {
    private val api = RetrofitClient.api

    suspend fun getOrderById(orderId: String): Order? {
        //return api.getOrder(orderId) // para quando a API estiver funcionando
        return mockGetOrder(orderId)
    }

    suspend fun updateOrder(orderId: String, updatedOrder: Order): Order {
        return api.updateOrder(orderId, updatedOrder) // para a implementação furua do botão de "iniciar preparo"
    }
}

