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

object StatusColors {
    val Pendente = Color(0xFF757575) to Color(0xFFF5F5F5)
    val EmPreparo = Color(0xFFFFA726) to Color(0xFFFFF3E0)
    val Entregue = Color(0xFF43A047) to Color(0xFFE8F5E9)
    val Cancelado = Color(0xFFD32F2F) to Color(0xFFFFEBEE)
    val Default = Color(0xFF616161) to Color(0xFFF5F5F5)
}

@Composable
fun TelaListaPedidos(pedidos: List<Pedido>) {
    var statusSelecionado by remember { mutableStateOf("Todos") }
    val listaStatus = listOf("Todos", "Pendente", "Em preparo", "Entregue", "Cancelado")

    val pedidosFiltrados = if (statusSelecionado == "Todos") pedidos
    else pedidos.filter { it.status == statusSelecionado }

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
            items(pedidosFiltrados, key = { it.usuario }) { pedido ->
                PedidoItem(pedido)
            }
        }
    }
}

@Composable
fun PedidoItem(pedido: Pedido) {
    val (corStatus, corFundo) = when (pedido.status) {
        "Entregue" -> StatusColors.Entregue
        "Cancelado" -> StatusColors.Cancelado
        "Em preparo" -> StatusColors.EmPreparo
        "Pendente" -> StatusColors.Pendente
        else -> StatusColors.Default
    }

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
                Text(pedido.usuario, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("Data: ${pedido.dataAbertura}", fontSize = 13.sp, color = Color.Gray)
            }
            Box(
                modifier = Modifier
                    .background(corFundo, shape = MaterialTheme.shapes.medium)
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(pedido.status, color = corStatus, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
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

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        TextField(
            value = statusSelecionado,
            onValueChange = {},
            label = { Text("Filtrar por status") },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
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
