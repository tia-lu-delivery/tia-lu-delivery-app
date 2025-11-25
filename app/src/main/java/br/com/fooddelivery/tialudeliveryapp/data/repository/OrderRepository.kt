package br.com.fooddelivery.tialudeliveryapp.data.repository

import br.com.fooddelivery.tialudeliveryapp.data.network.RetrofitClient // Supondo que você tem isso
import br.com.fooddelivery.tialudeliveryapp.model.Order
import br.com.fooddelivery.tialudeliveryapp.model.OrderItem
import br.com.fooddelivery.tialudeliveryapp.model.OrderStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

val mockData = mutableListOf(
    Order(
        orderNumber = "1020",
        openingTime = "11/10/2025, 11:50",
        status = OrderStatus.ACEITO, // Em Preparação
        customerName = "Maria",
        customerPhone = "(75) 99999-0000",
        deliveryAddress = "Rua Francisco, 123",
        items = mutableListOf(OrderItem("1", "Coca-cola Zero", 2, 10.0), OrderItem("0", "Guaraná", 2, 10.0)),
        restaurantName = "Restaurante Central",
        totalValue = 20.0 * 2.0 // R$ 40,00
    ),
    Order(
        orderNumber = "1021",
        openingTime = "10/10/2025, 15:30",
        status = OrderStatus.ENTREGUE,
        customerName = "Bia",
        customerPhone = "(75) 99999-0000",
        deliveryAddress = "Rua Francisco, 123",
        items = mutableListOf(OrderItem("1", "Pizza", 2, 50.0), OrderItem("0", "Guaraná", 1, 10.0)),
        restaurantName = "Pizzaria Bela",
        totalValue = 110.0
    ),
    Order(
        orderNumber = "1022",
        openingTime = "09/10/2025, 20:10",
        status = OrderStatus.SAIU_PARA_ENTREGA, // Em Preparação (ainda ativo)
        customerName = "Valeria",
        customerPhone = "(75) 99999-0000",
        deliveryAddress = "Rua Francisco, 123",
        items = mutableListOf(OrderItem("1", "Macarronada", 1, 35.0)),
        restaurantName = "Sabor da Itália",
        totalValue = 35.0
    ),
    Order(
        orderNumber = "1023",
        openingTime = "08/10/2025, 19:45",
        status = OrderStatus.ABERTO, // Simular Cancelado ou pendente
        customerName = "Ian",
        customerPhone = "(75) 99999-0000",
        deliveryAddress = "Rua Francisco, 123",
        items = mutableListOf(OrderItem("1", "Coca-cola", 1, 5.0), OrderItem("0", "Guaraná", 1, 5.0)),
        restaurantName = "Lanches Rápidos",
        totalValue = 10.0
    ),
    // Pedidos adicionais para simular lista longa
    Order(orderNumber = "1024", openingTime = "07/10/2025, 12:00", status = OrderStatus.ENTREGUE, customerName = "A", customerPhone = "A", deliveryAddress = "A", items = mutableListOf(), restaurantName = "X Burguer", totalValue = 15.0),
    Order(orderNumber = "1025", openingTime = "06/10/2025, 18:30", status = OrderStatus.FEITO, customerName = "B", customerPhone = "B", deliveryAddress = "B", items = mutableListOf(), restaurantName = "Açaí Top", totalValue = 25.0),
    Order(orderNumber = "1026", openingTime = "05/10/2025, 19:15", status = OrderStatus.ENTREGUE, customerName = "C", customerPhone = "C", deliveryAddress = "C", items = mutableListOf(), restaurantName = "Pizzaria Forno", totalValue = 80.0),
    Order(orderNumber = "1027", openingTime = "04/10/2025, 22:00", status = OrderStatus.ACEITO, customerName = "D", customerPhone = "D", deliveryAddress = "D", items = mutableListOf(), restaurantName = "Central", totalValue = 12.0),
    Order(orderNumber = "1028", openingTime = "03/10/2025, 16:40", status = OrderStatus.ENTREGUE, customerName = "E", customerPhone = "E", deliveryAddress = "E", items = mutableListOf(), restaurantName = "Café Expresso", totalValue = 9.50)
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

    suspend fun getAllOrders(): List<Order> {
        // retorno da API
        // return api.getOrders(page, size)

        return mockData
    }
}

