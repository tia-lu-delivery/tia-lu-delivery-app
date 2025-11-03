package br.com.fooddelivery.tialudeliveryapp.presentation.components

import br.com.fooddelivery.tialudeliveryapp.data.model.PendingOrder
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.foundation.shape.RoundedCornerShape 

val TiaLuOrange = Color(0xFFFF9800)
val TiaLuRed = Color(0xFFF44336)

@Composable
fun OrderCard(
    order: PendingOrder,
    onAcceptClick: () -> Unit,
    onRejectClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Cliente: ${order.customerName}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Status: ${order.status}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Itens: ${order.itemCount}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))
            Column(modifier = Modifier.padding(start = 8.dp)) {
                order.items.forEach { item ->
                    Column(modifier = Modifier.padding(bottom = 4.dp)) {
                        Text(
                            text = "${item.quantity}x ${item.name}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodySmall,
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Total: R$ ${order.totalPrice}",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onAcceptClick() },
                    colors = ButtonDefaults.buttonColors(containerColor = TiaLuOrange),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Aceitar", color = Color.White)
                }

                Button(
                    onClick = { onRejectClick() },
                    colors = ButtonDefaults.buttonColors(containerColor = TiaLuRed),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Rejeitar", color = Color.White)
                }
            }
        }
    }
}