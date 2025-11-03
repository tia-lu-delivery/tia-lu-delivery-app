package br.com.fooddelivery.tialudeliveryapp

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

// Agora usamos OrderPresentation vindo do ViewModel
data class OrderPresentation(
    val usuario: String,
    val openedAtFormatted: String,
    val statusLabel: String,
    val statusColor: Color,
    val statusBackground: Color
)

// Estado exposto pelo ViewModel
data class OrdersUiState(
    val orders: List<OrderPresentation> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

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
            onStatusChange = { onSelectFilter(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
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
                    items(uiState.orders) { pedido ->
                        PedidoItem(pedido)
                    }
                }
            }
        }
    }
}

@Composable
fun PedidoItem(pedido: OrderPresentation) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
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
                Text(
                    text = pedido.usuario,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1C)
                )
                Text(
                    text = "Data: ${pedido.openedAtFormatted}",
                    fontSize = 13.sp,
                    color = Color(0xFF757575)
                )
            }

            Box(
                modifier = Modifier
                    .background(
                        color = pedido.statusBackground,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = pedido.statusLabel,
                    color = pedido.statusColor,
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
    var selectedLabel by remember { mutableStateOf(listaStatus.firstOrNull()?.second ?: "Todos") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedLabel,
            onValueChange = {},
            label = { Text("Filtrar por status") },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFF3E0),
                unfocusedContainerColor = Color(0xFFFFF3E0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color(0xFFFF9800),
                cursorColor = Color(0xFFFF9800),
                focusedTextColor = Color(0xFF1C1C1C),
                unfocusedTextColor = Color(0xFF1C1C1C)
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listaStatus.forEach { (orderStatus, labelPt) ->
                DropdownMenuItem(
                    text = { Text(labelPt) },
                    onClick = {
                        selectedLabel = labelPt
                        onStatusChange(orderStatus)
                        expanded = false
                    }
                )
            }
        }
    }
}
