package br.com.fooddelivery.tialudeliveryapp.data.repository

import br.com.fooddelivery.tialudeliveryapp.data.model.OrderItem
import br.com.fooddelivery.tialudeliveryapp.data.model.PendingOrder
import kotlinx.coroutines.delay

/**
 * SIMULAÇÃO TEMPORÁRIA da camada de dados (Tarefa #21)
*/

class OrdersRepository {

    // Simula a busca pelo status 'Pending' na API, com 1.5s de espera da rede
    suspend fun getPendingOrders(): List<PendingOrder> {
        delay(1500)

        // Dados ficctícios de exemplo
        return listOf(
            PendingOrder(
                id = "1",
                orderNumber = "ORD-001",
                customerName = "João Silva",
                items = listOf(
                    OrderItem("1", "Macarrão", "Com camarão e porco", 1, 7.50)
                ),
                totalPrice = 7.50,
                orderTime = "10:30",
            ),
            PendingOrder(
                id = "2",
                orderNumber = "ORD-002",
                customerName = "Maria Oliveira",
                items = listOf(
                    OrderItem("2", "Pasta", "Molho branco", 2, 6.20)
                ),
                totalPrice = 12.40,
                orderTime = "11:00"
            )
        )
    }

    // Simula a chamada para aceitar um pedido (ex: POST /orders/1/accept)
    suspend fun acceptOrder(orderId: String): Boolean {
        delay(1000) // Simula 1s de espera
        println("API: Pedido $orderId ACEITO com sucesso.")
        return true // Simula sucesso
    }

    // Simula a chamada para rejeitar um pedido (ex: POST /orders/2/reject)
    suspend fun rejectOrder(orderId: String): Boolean {
        delay(1000) // Simula 1s de espera
        println("API: Pedido $orderId REJEITADO com sucesso.")
        return true // Simula sucesso
    }
}

/*Substituir depois da API pronta:
package br.com.fooddelivery.tialudeliveryapp.data.repository

import br.com.fooddelivery.tialudeliveryapp.data.model.PendingOrder
import br.com.fooddelivery.tialudeliveryapp.data.remote.RetrofitClient

class OrdersRepository {

    private val apiService = RetrofitClient.orderService

    // Chama GET /orders/list?status=Pending
    suspend fun getPendingOrders(): List<PendingOrder> {
        val response = apiService.getOrdersByStatus("Pending")

        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Erro ao buscar pedidos pendentes: ${response.code()} - ${response.message()}")
        }
    }
}*/
