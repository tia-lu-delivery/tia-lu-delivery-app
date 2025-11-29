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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fooddelivery.tialudeliveryapp.model.OrderPresentation
import br.com.fooddelivery.tialudeliveryapp.model.OrderStatus
import br.com.fooddelivery.tialudeliveryapp.model.toLabelPt
import androidx.compose.material3.ExposedDropdownMenuAnchorType

@OptIn(ExperimentalMaterial3Api::class)
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
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1C1C1C)
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        PedidoStatusDropdown(
            expanded = expanded,
            selectedLabel = activeFilter?.toLabelPt() ?: "Todos",
            filters = availableFilters,
            onExpandChange = { expanded = !expanded },
            onFilterSelected = {
                onFilterChange(it)
                expanded = false
            }
        )

        Spacer(Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> ErroContent(errorMessage, onRetry)

            else -> ListaPedidosContent(orders)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PedidoStatusDropdown(
    expanded: Boolean,
    selectedLabel: String,
    filters: List<Pair<OrderStatus?, String>>,
    onExpandChange: () -> Unit,
    onFilterSelected: (OrderStatus?) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandChange() }
    ) {
        TextField(
            value = selectedLabel,
            onValueChange = {},
            readOnly = true,
            label = { Text("Filtrar por status") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandChange() }
        ) {
            filters.forEach { (status, label) ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = { onFilterSelected(status) }
                )
            }
        }
    }
}

@Composable
private fun ListaPedidosContent(
    orders: List<OrderPresentation>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        items(orders, key = { it.id }) { pedido ->
            PedidoItemCard(pedido)
        }
    }
}

@Composable
private fun ErroContent(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Erro: $errorMessage", color = Color.Red)
        Spacer(Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Tentar novamente")
        }
    }
}

@Composable
private fun PedidoItemCard(pedido: OrderPresentation) {
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
                Text(
                    "Data: ${pedido.openedAtFormatted}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            StatusBadge(
                label = pedido.statusLabel,
                status = pedido.status
            )
        }
    }
}

@Composable
private fun StatusBadge(label: String, status: OrderStatus) {
    val (bg, text) = when (status) {
        OrderStatus.ENTREGUE -> Color(0xFFE8F5E9) to Color(0xFF43A047)
        OrderStatus.CANCELADO -> Color(0xFFFFEBEE) to Color(0xFFD32F2F)
        OrderStatus.FAZENDO -> Color(0xFFFFF3E0) to Color(0xFFFFA726)
        OrderStatus.ACEITO -> Color(0xFFF5F5F5) to Color(0xFF757575)
        else -> Color(0xFFF5F5F5) to Color(0xFF616161)
    }

    Box(
        modifier = Modifier
            .background(bg, shape = MaterialTheme.shapes.medium)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(label, color = text, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun TelaListaPedidosPreview() {
    val sampleOrders = listOf(
        OrderPresentation(
            id = "1",
            customerName = "João",
            openedAtFormatted = "25/11/2025 12:00",
            status = OrderStatus.ACEITO,
            statusLabel = OrderStatus.ACEITO.toLabelPt()
        ),
        OrderPresentation(
            id = "2",
            customerName = "Maria",
            openedAtFormatted = "25/11/2025 13:00",
            status = OrderStatus.ENTREGUE,
            statusLabel = OrderStatus.ENTREGUE.toLabelPt()
        )
    )

    TelaListaPedidos(
        orders = sampleOrders,
        isLoading = false,
        errorMessage = null,
        availableFilters = listOf(
            null to "Todos",
            OrderStatus.ACEITO to "Aceito",
            OrderStatus.ENTREGUE to "Entregue",
            OrderStatus.FAZENDO to "Fazendo",
            OrderStatus.CANCELADO to "Cancelado"
        ),
        activeFilter = null,
        onFilterChange = {},
        onRetry = {}
    )
}
