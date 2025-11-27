package br.com.fooddelivery.tialudeliveryapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fooddelivery.tialudeliveryapp.data.model.PaymentMethod
import br.com.fooddelivery.tialudeliveryapp.presentation.components.PaymentMethodCard
import br.com.fooddelivery.tialudeliveryapp.presentation.viewmodel.PaymentMethodsUiState
import br.com.fooddelivery.tialudeliveryapp.presentation.viewmodel.PaymentMethodsViewModel

val TiaLuOrange = Color(0xFFFF9800)

@Composable
fun PaymentMethodsScreen(
    viewModel: PaymentMethodsViewModel = viewModel()
) {
    // 1. Observa o estado que vem do ViewModel (Rebeca)
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // Cabeçalho Laranja
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

        // 2. Gerencia os estados da tela (Carregando, Erro ou Sucesso)
        when (val state = uiState) {
            is PaymentMethodsUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = TiaLuOrange)
                }
            }
            is PaymentMethodsUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.message, color = MaterialTheme.colorScheme.error)
                }
            }
            is PaymentMethodsUiState.Success -> {
                // 3. Se for sucesso, exibe a lista que veio do Ismael/Rebeca
                LazyColumn {
                    items(state.methods) { metodo ->
                        PaymentMethodCard(method = metodo)
                    }
                }
            }
        }
    }
}

// O Preview continua usando dados falsos apenas para visualização no Android Studio
@Preview(showBackground = true)
@Composable
fun PreviewPaymentMethodsScreen() {
    val listaFalsa = listOf(
        PaymentMethod("1", "", "1234", "CRÉDITO", "PREVIEW LTDA", "12/30"),
        PaymentMethod("2", "", "9876", "DÉBITO", "JOÃO PREVIEW", "05/25")
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