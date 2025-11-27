package br.com.fooddelivery.tialudeliveryapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fooddelivery.tialudeliveryapp.data.model.PaymentMethod

@Composable
fun PaymentMethodCard(method: PaymentMethod) {
    val tiaLuOrange = Color(0xFFFF9800)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone da Bandeira
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CreditCard,
                    contentDescription = "Bandeira do cartão",
                    tint = tiaLuOrange // Cor laranja
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Textos
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = method.cardType,
                        style = MaterialTheme.typography.labelSmall,
                        color = tiaLuOrange, // Cor laranja
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "•••• ${method.last4Digits}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = method.holderName,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = "Validade: ${method.expiryDate}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCard() {
    val fakeMethod = PaymentMethod(
        id = "1",
        flagUrl = "",
        last4Digits = "8888",
        cardType = "CRÉDITO",
        holderName = "MARIA SILVA",
        expiryDate = "10/29"
    )
    PaymentMethodCard(fakeMethod)
}