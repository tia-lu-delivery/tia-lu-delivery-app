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
        val query = status?.name?.lowercase()  // null -> Retrofit omite

        val dtos = api.getOrdersByStatus(query)

        return dtos.mapNotNull { dto ->
            val mappedStatus = mapStatusFromApi(dto.status) ?: return@mapNotNull null

            try {
                Order(
                    id = dto.id,
                    userName = dto.userName,
                    customerName = dto.customerName,
                    items = dto.items.map { it.toDomain() },   // <-- corrigido
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

    // ---------- NORMALIZAÇÃO ROBUSTA ----------
    private fun normalize(text: String?): String? {
        if (text.isNullOrBlank()) return null

        val normalized = Normalizer.normalize(text, Normalizer.Form.NFD)
        return normalized
            .replace(Regex("\\p{Mn}+"), "") // remove acentos
            .lowercase()
            .trim()
    }

    // ---------- MAPEAMENTO DE STATUS ----------
    private fun mapStatusFromApi(status: String?): OrderStatus? {
        val s = normalize(status) ?: return null

        return when (s) {

            "aceito", "accepted", "accept" ->
                OrderStatus.ACEITO

            "em preparo", "preparando", "preparing", "cooking" ->
                OrderStatus.FAZENDO

            "feito", "done", "ready" ->
                OrderStatus.FEITO

            "saiu para entrega", "out for delivery", "out_for_delivery" ->
                OrderStatus.SAIU_PARA_ENTREGA

            "entregue", "delivered" ->
                OrderStatus.ENTREGUE

            else -> null
        }
    }
}

// -------------------- MAPPER CORRETO --------------------
private fun OrderItemDto.toDomain(): OrderItem {
    return OrderItem(
        id = this.id,
        name = this.name,
        quantity = this.quantity,
        price = this.price
    )
}
