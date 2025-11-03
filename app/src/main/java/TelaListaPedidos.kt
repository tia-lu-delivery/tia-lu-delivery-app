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

data class Pedido(
    val usuario: String,
    val dataAbertura: String,
    val status: String
)

@Composable
fun TelaListaPedidos(pedidos: List<Pedido>) {
    var statusSelecionado by remember { mutableStateOf("Todos") }
    val listaStatus = listOf("Todos", "Pendente", "Em preparo", "Entregue", "Cancelado")

    val pedidosFiltrados = if (statusSelecionado == "Todos") pedidos
    else pedidos.filter { it.status == statusSelecionado }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)) // Fundo branco
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
            listaStatus = listaStatus,
            statusSelecionado = statusSelecionado,
            onStatusChange = { statusSelecionado = it }
        )

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(pedidosFiltrados) { pedido ->
                PedidoItem(pedido)
            }
        }
    }
}

@Composable
fun PedidoItem(pedido: Pedido) {
    val corStatus = when (pedido.status) {
        "Entregue" -> Color(0xFF43A047)
        "Cancelado" -> Color(0xFFD32F2F)
        "Em preparo" -> Color(0xFFFFA726)
        "Pendente" -> Color(0xFF757575)
        else -> Color(0xFF616161)
    }

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
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = pedido.usuario,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1C)
                )
                Text(
                    text = "Data: ${pedido.dataAbertura}",
                    fontSize = 13.sp,
                    color = Color(0xFF757575)
                )
            }

            Box(
                modifier = Modifier
                    .background(
                        color = when (pedido.status) {
                            "Entregue" -> Color(0xFFE8F5E9)
                            "Cancelado" -> Color(0xFFFFEBEE)
                            "Em preparo" -> Color(0xFFFFF3E0)
                            else -> Color(0xFFF5F5F5)
                        },
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = pedido.status,
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
    listaStatus: List<String>,
    statusSelecionado: String,
    onStatusChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = statusSelecionado,
            onValueChange = {},
            label = { Text("Filtrar por status") },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFF3E0), // Laranja clarinho
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
            listaStatus.forEach { status ->
                DropdownMenuItem(
                    text = { Text(status) },
                    onClick = {
                        onStatusChange(status)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaListaPedidos() {
    val pedidosExemplo = listOf(
        Pedido("João Silva", "01/11/2025", "Pendente"),
        Pedido("Maria Santos", "30/10/2025", "Em preparo"),
        Pedido("Carlos Oliveira", "29/10/2025", "Entregue"),
        Pedido("Ana Costa", "28/10/2025", "Cancelado")
    )
    TelaListaPedidos(pedidos = pedidosExemplo)
}
