package br.com.fooddelivery.tialudeliveryapp.data.repository

import br.com.fooddelivery.tialudeliveryapp.data.network.OrderItemDto
import br.com.fooddelivery.tialudeliveryapp.data.network.OrdersApi
import br.com.fooddelivery.tialudeliveryapp.model.Order
import br.com.fooddelivery.tialudeliveryapp.model.OrderItem
import br.com.fooddelivery.tialudeliveryapp.model.OrderStatus
import java.text.Normalizer

class OrdersRepositoryImpl(
    private val api: OrdersApi
) : OrdersRepository {

    override suspend fun fetchOrders(): List<Order> =
        fetchOrdersByStatus(null)

    override suspend fun fetchOrdersByStatus(status: OrderStatus?): List<Order> {
        val query = status?.name?.lowercase()
        val dtos = api.getOrdersByStatus(query)

        return dtos.mapNotNull { dto ->
            val mappedStatus = mapStatusFromApi(dto.status) ?: return@mapNotNull null

            try {
                // Prefer customerName; se nulo, usa userName; se ambos nulos, usa placeholder
                val customer = dto.customerName ?: dto.userName ?: "Cliente"

                Order(
                    id = dto.id,
                    customerName = customer,
                    items = dto.items.map { it.toDomain() },
                    totalPrice = dto.totalPrice,
                    openedAt = dto.openedAt,
                    status = mappedStatus
                )
            } catch (e: Exception) {
                println("Erro ao mapear pedido id=${dto.id}: ${e.message}")
                null
            }
        }
    }

    private fun normalize(text: String?): String? {
        if (text.isNullOrBlank()) return null
        val normalized = Normalizer.normalize(text, Normalizer.Form.NFD)
        return normalized.replace(Regex("\\p{Mn}+"), "").lowercase().trim()
    }

    private fun mapStatusFromApi(status: String?): OrderStatus? {
        val s = normalize(status) ?: return null
        return when (s) {
            "aceito", "accepted", "accept" -> OrderStatus.ACEITO
            "em preparo", "preparando", "preparing", "cooking" -> OrderStatus.FAZENDO
            "feito", "done", "ready" -> OrderStatus.FEITO
            "saiu para entrega", "out for delivery", "out_for_delivery" -> OrderStatus.SAIU_PARA_ENTREGA
            "entregue", "delivered" -> OrderStatus.ENTREGUE
            "cancelado", "canceled", "cancelled" -> OrderStatus.CANCELADO
            "rejeitado", "rejected" -> OrderStatus.REJEITADO
            else -> null
        }
    }
}

private fun OrderItemDto.toDomain(): OrderItem {
    return OrderItem(
        id = this.id,
        name = this.name,
        quantity = this.quantity,
        price = this.price
    )
}
