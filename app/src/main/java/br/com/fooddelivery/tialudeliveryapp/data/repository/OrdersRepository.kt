package br.com.fooddelivery.tialudeliveryapp.data.repository

import br.com.fooddelivery.tialudeliveryapp.data.model.OrderItem
import br.com.fooddelivery.tialudeliveryapp.data.model.PendingOrder
import kotlinx.coroutines.delay

/**
 * SIMULAÇÃO TEMPORÁRIA da camada de dados (Tarefa #21)
 * Os imports acima inclusive estão pegando as classes
 * que Fátima implementou, como não estão "sincronizados"
 * eu colei todas as pastas aqui, senão ia ficar como se fosse erro.
 * Aí eu ia ficar codando no escuro praticamente, não pensei numa solução melhor
 * e queria que ficasse tudo ligadinho já. (Apesar que esse é o arquivo temporário e Ismael que vai fazer o outr0
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