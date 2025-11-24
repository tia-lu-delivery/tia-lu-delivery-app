package br.com.fooddelivery.tialudeliveryapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fooddelivery.tialudeliveryapp.model.OrderPresentation
import br.com.fooddelivery.tialudeliveryapp.model.OrderStatus

@Composable
fun TelaListaPedidos(
    orders: List<OrderPresentation>,
    isLoading: Boolean,
    errorMessage: String?,
    availableFilters: List<Pair<OrderStatus?, String>>,
    activeFilter: OrderStatus?,
    onFilterChange: (OrderStatus?) -> Unit,
    onRetry: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Meus Pedidos",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color(0xFF1C1C1C),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                value = activeFilter?.toLabelPt() ?: "Todos",
                onValueChange = {},
                label = { Text("Filtrar por status") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                availableFilters.forEach { (status, label) ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            onFilterChange(status)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Column
        }

        if (errorMessage != null) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Erro: $errorMessage")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onRetry) { Text("Tentar novamente") }
            }
            return@Column
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(orders, key = { it.id }) { pedido ->
                PedidoItemPresentation(pedido)
            }
        }
    }
}

@Composable
private fun PedidoItemPresentation(pedido: OrderPresentation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(pedido.customerName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("Data: ${pedido.openedAtFormatted}", fontSize = 13.sp, color = Color.Gray)
            }
            Box(
                modifier = Modifier
                    .background(
                        when (pedido.status) {
                            OrderStatus.ENTREGUE -> Color(0xFFE8F5E9)
                            OrderStatus.CANCELADO -> Color(0xFFFFEBEE)
                            OrderStatus.FAZENDO -> Color(0xFFFFF3E0)
                            OrderStatus.ACEITO -> Color(0xFFF5F5F5)
                            else -> Color(0xFFF5F5F5)
                        },
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(pedido.statusLabel, color = when (pedido.status) {
                    OrderStatus.ENTREGUE -> Color(0xFF43A047)
                    OrderStatus.CANCELADO -> Color(0xFFD32F2F)
                    OrderStatus.FAZENDO -> Color(0xFFFFA726)
                    OrderStatus.ACEITO -> Color(0xFF757575)
                    else -> Color(0xFF616161)
                }, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            }
        }
    }
}
