package br.com.fooddelivery.tialudeliveryapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fooddelivery.tialudeliveryapp.data.model.OrderItem
import br.com.fooddelivery.tialudeliveryapp.data.model.PendingOrder
import br.com.fooddelivery.tialudeliveryapp.presentation.components.OrderCard
import br.com.fooddelivery.tialudeliveryapp.presentation.viewmodel.PendingOrdersUiState
import br.com.fooddelivery.tialudeliveryapp.presentation.viewmodel.PendingOrdersViewModel


@Composable
fun PendingOrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: PendingOrdersViewModel = viewModel()
) {
    // Observa o estado (uiState) vindo do ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Pedidos Pendentes",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Reage aos diferentes estados (Loading, Error, Success)
        when (val state = uiState) {
            is PendingOrdersUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is PendingOrdersUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.message, color = MaterialTheme.colorScheme.error)
                }
            }
            is PendingOrdersUiState.Success -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(state.orders) { order -> // Usa state.orders
                        OrderCard(
                            order = order,
                            onAcceptClick = {
                                viewModel.acceptOrder(order.id)
                            },
                            onRejectClick = {
                                viewModel.rejectOrder(order.id)
                            }
                        )
                    }
                }
            }
        }
    }
}


// O Preview permanece inalterado para não quebrar a visualização
@Preview(showBackground = true)
@Composable
fun PreviewPendingOrdersScreen() {
    // Dados de exemplo apenas para o Preview
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

    // UI simulada para o Preview
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
                    onAcceptClick = { }, // Preview não faz nada
                    onRejectClick = { }  // Preview não faz nada
                )
            }
        }
    }
}