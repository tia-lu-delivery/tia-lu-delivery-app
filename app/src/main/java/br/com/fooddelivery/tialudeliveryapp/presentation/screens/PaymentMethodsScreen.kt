package br.com.fooddelivery.tialudeliveryapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fooddelivery.tialudeliveryapp.data.model.PaymentMethod
import br.com.fooddelivery.tialudeliveryapp.presentation.components.PaymentMethodCard

val TiaLuOrange = Color(0xFFFF9800)

@Composable
fun PaymentMethodsScreen() {
    // Tela vazia esperando integração
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Aguardando integração com ViewModel...")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentMethodsScreen() {
    br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme {
        val listaFalsa = listOf(
            PaymentMethod("1", "", "1234", "CRÉDITO", "EMPRESA LTDA", "12/30"),
            PaymentMethod("2", "", "9876", "DÉBITO", "JOÃO DA SILVA", "05/25"),
            PaymentMethod("3", "", "5678", "VOUCHER", "MARIA OLIVEIRA", "01/28")
        )

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            Surface(
                color = TiaLuOrange,
                shape = RoundedCornerShape(50.dp),
            ) {
                Text(
                    text = "Meios de Pagamento",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn {
                items(listaFalsa) { metodo ->
                    PaymentMethodCard(method = metodo)
                }
            }
        }
    }
}