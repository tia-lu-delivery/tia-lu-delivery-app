package br.com.fooddelivery.tialudeliveryapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fooddelivery.tialudeliveryapp.ui.components.CardType
import br.com.fooddelivery.tialudeliveryapp.ui.components.RegistrationTextField
import br.com.fooddelivery.tialudeliveryapp.ui.theme.OrangeColor
import br.com.fooddelivery.tialudeliveryapp.ui.theme.ScreenBgColor
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme
import br.com.fooddelivery.tialudeliveryapp.ui.viewmodel.PaymentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodScreen(
    onNavigateBack: () -> Unit = {},
    onSavePaymentSuccess: () -> Unit = {},
    viewModel: PaymentViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val apiError by viewModel.apiError.collectAsState()
    LaunchedEffect(apiError) {
        apiError?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        containerColor = ScreenBgColor,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "Cadastro de Pagamento",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text("Tipo de Cartão", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CardType(
                    "Crédito",
                    uiState.selectedType == "CREDITO",
                    { viewModel.updateCardType("CREDITO") },
                    Modifier.weight(1f)
                )
                CardType(
                    "Débito",
                    uiState.selectedType == "DEBITO",
                    { viewModel.updateCardType("DEBITO") },
                    Modifier.weight(1f)
                )
                CardType(
                    "Refeição",
                    uiState.selectedType == "REFEICAO",
                    { viewModel.updateCardType("REFEICAO") },
                    Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(24.dp))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                RegistrationTextField(
                    label = "Número do Cartão",
                    placeholder = "0000 0000 0000 0000",
                    value = uiState.cardNumber,
                    onValueChange = { viewModel.updateCardNumber(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.cardError,
                    subtitle = if (uiState.cardError) "Cartão inválido" else null
                )

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(modifier = Modifier.weight(1f)) {
                        RegistrationTextField(
                            label = "Validade",
                            placeholder = "MM/AA",
                            value = uiState.expiryDate,
                            onValueChange = { viewModel.updateExpiryDate(it) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = uiState.dateError,
                            subtitle = if (uiState.dateError) "Data inválida" else null
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        RegistrationTextField(
                            label = "CVV",
                            placeholder = "000",
                            value = uiState.cvv,
                            onValueChange = { viewModel.updateCvv(it) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            isError = uiState.cvvError
                        )
                    }
                }

                RegistrationTextField(
                    label = "Nome no Cartão",
                    placeholder = "Nome igual a do cartão",
                    value = uiState.cardName,
                    onValueChange = { viewModel.updateCardName(it) }
                )


                RegistrationTextField(
                    label = "CPF do Titular",
                    placeholder = "000.000.000-00",
                    value = uiState.cpf,
                    onValueChange = { viewModel.updateCpf(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.cpfError,
                    subtitle = if (uiState.cpfError) "CPF inválido" else null
                )
            }

            Spacer(Modifier.weight(1f))
            Spacer(Modifier.height(32.dp))


            Button(
                onClick = {

                    val userId = "user9001"

                    if (uiState.isValid) {

                        viewModel.savePayment(userId = userId, onSuccess = onSavePaymentSuccess)
                    } else {

                        val msg = if (uiState.cardNumber.isBlank() || uiState.cardName.isBlank()) {
                            "Preencha todos os campos"
                        } else {
                            "Corrija os campos em vermelho"
                        }
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                },

                enabled = uiState.isValid && !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = OrangeColor)
            ) {
                if (uiState.isLoading) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Salvar Cartão", fontSize = 16.sp, color = Color.White)
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentScreenPreview() {
    TiaLuDeliveryAppTheme {
        PaymentMethodScreen()
    }
}