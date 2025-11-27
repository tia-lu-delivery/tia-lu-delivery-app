package br.com.fooddelivery.tialudeliveryapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fooddelivery.tialudeliveryapp.data.model.OrderItem
import br.com.fooddelivery.tialudeliveryapp.data.model.PendingOrder
import br.com.fooddelivery.tialudeliveryapp.presentation.components.OrderCard


@Composable
fun PendingOrdersScreen() {
    // Simulação temporária de pedidos (antes de fazermos o ViewModel)
    val sampleOrders = listOf(
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

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Pedidos Pendentes",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(sampleOrders) { order ->


                OrderCard(
                    order = order,
                    onAcceptClick = {
                        //Aguardando o ViewModel para ACEITAR o pedido (order.id)
                    },
                    onRejectClick = {
                        //Aguardando o ViewModel para REJEITAR o pedido (order.id)
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPendingOrdersScreen() {
    PendingOrdersScreen()
}



