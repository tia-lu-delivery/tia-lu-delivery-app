package br.com.fooddelivery.tialudeliveryapp.data.network

class FakeOrderApi : OrdersApi {
    override suspend fun getOrdersByStatus(status: String?): List<OrderDto> {
        // Simula alguns pedidos de teste
        return listOf(
            OrderDto(
                id = "1",
                userName = "João",
                customerName = null,
                openedAt = "2025-11-25T12:00:00Z",
                customerPhone = "99999-9999",
                deliveryAddress = "Rua A, 123",
                items = listOf(
                    OrderItemDto("i1", "Pizza", 2, 50.0),
                    OrderItemDto("i2", "Refrigerante", 1, 8.0)
                ),
                totalPrice = 108.0,
                status = "aceito"
            ),
            OrderDto(
                id = "2",
                userName = "Maria",
                customerName = null,
                openedAt = "2025-11-25T13:00:00Z",
                customerPhone = "88888-8888",
                deliveryAddress = "Rua B, 456",
                items = listOf(
                    OrderItemDto("i3", "Hambúrguer", 1, 25.0)
                ),
                totalPrice = 25.0,
                status = "entregue"
            )
        )
    }
}