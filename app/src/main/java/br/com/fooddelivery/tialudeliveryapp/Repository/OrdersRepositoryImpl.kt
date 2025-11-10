package br.com.fooddelivery.tialudeliveryapp.data.repository

import br.com.fooddelivery.tialudeliveryapp.data.network.OrdersApi
import br.com.fooddelivery.tialudeliveryapp.domain.Order
import br.com.fooddelivery.tialudeliveryapp.domain.OrderStatus
import java.time.Instant

class OrdersRepositoryImpl(
    private val api: OrdersApi
) : OrdersRepository {

    /**
     * Busca todos os pedidos sem filtrar por status.
     */
    override suspend fun fetchOrders(): List<Order> {
        return fetchOrdersByStatus(null)
    }

    /**
     * Busca pedidos filtrando por status, se fornecido.
     */
    override suspend fun fetchOrdersByStatus(status: OrderStatus?): List<Order> {
        val query = status?.name?.lowercase() ?: ""
        val dtos = api.getOrdersByStatus(query)

        return dtos.mapNotNull { dto ->
            val mappedStatus = mapStatusFromApi(dto.status)
            if (mappedStatus == OrderStatus.UNKNOWN) return@mapNotNull null

            try {
                Order(
                    id = dto.id,
                    userName = dto.userName,
                    openedAt = Instant.parse(dto.openedAt),
                    status = mappedStatus
                )
            } catch (e: Exception) {
                null // ignora pedidos com data inválida
            }
        }
    }

    /**
     * Mapeia o status recebido da API para o enum de domínio.
     * Aceita variações em português e inglês, com ou sem acento.
     */
    fun mapStatusFromApi(status: String?): OrderStatus {
        return when (status?.lowercase()?.replace("ã", "a")?.replace("é", "e")?.replace("ê", "e")) {
            "aberto", "open" -> OrderStatus.OPEN
            "preparando", "em preparo", "preparing" -> OrderStatus.PREPARING
            "entregue", "entregado", "delivered" -> OrderStatus.DELIVERED
            "cancelado", "cancelled" -> OrderStatus.CANCELLED
            else -> OrderStatus.UNKNOWN
        }
    }
}