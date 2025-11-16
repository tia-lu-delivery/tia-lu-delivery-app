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
import br.com.fooddelivery.tialudeliveryapp.viewmodel.OrdersUiState

@Composable
fun TelaListaPedidos(
    uiState: OrdersUiState,
    availableFilters: List<Pair<OrderStatus?, String>>,
    onSelectFilter: (OrderStatus?) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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

        StatusFilterDropdown(
            listaStatus = availableFilters,
            onStatusChange = onSelectFilter
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.errorMessage != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = uiState.errorMessage, color = Color.Red)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onRetry) {
                        Text("Tentar novamente")
                    }
                }
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(uiState.orders, key = { it.id }) { pedido ->
                        PedidoItem(pedido)
                    }
                }
            }
        }
    }
}

@Composable
fun PedidoItem(pedido: OrderPresentation) {
    val (corStatus, corFundo) = when (pedido.status) {
        OrderStatus.DELIVERED -> Color(0xFF43A047) to Color(0xFFE8F5E9)
        OrderStatus.CANCELED -> Color(0xFFD32F2F) to Color(0xFFFFEBEE)
        OrderStatus.COOKING -> Color(0xFFFFA726) to Color(0xFFFFF3E0)
        OrderStatus.AWAITING_APPROVAL -> Color(0xFF757575) to Color(0xFFF5F5F5)
        else -> Color(0xFF616161) to Color(0xFFF5F5F5)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(pedido.userName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("Data: ${pedido.openedAtFormatted}", fontSize = 13.sp, color = Color.Gray)
            }
            Box(
                modifier = Modifier
                    .background(corFundo, shape = MaterialTheme.shapes.medium)
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = pedido.statusLabel,
                    color = corStatus,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusFilterDropdown(
    listaStatus: List<Pair<OrderStatus?, String>>,
    onStatusChange: (OrderStatus?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedLabel by remember {
        mutableStateOf(listaStatus.firstOrNull()?.second ?: "Todos")
    }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        TextField(
            value = selectedLabel,
            onValueChange = {},
            label = { Text("Filtrar por status") },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            listaStatus.forEach { (status, labelPt) ->
                DropdownMenuItem(
                    text = { Text(labelPt) },
                    onClick = {
                        selectedLabel = labelPt
                        onStatusChange(status)
                        expanded = false
                    }
                )
            }
        }
    }
}
