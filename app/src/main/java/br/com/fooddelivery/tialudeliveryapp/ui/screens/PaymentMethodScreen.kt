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
import br.com.fooddelivery.tialudeliveryapp.ui.components.CardType

import br.com.fooddelivery.tialudeliveryapp.ui.components.RegistrationTextField
import br.com.fooddelivery.tialudeliveryapp.ui.theme.OrangeColor
import br.com.fooddelivery.tialudeliveryapp.ui.theme.ScreenBgColor
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme
import br.com.fooddelivery.tialudeliveryapp.ui.utils.Validators

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodScreen(
    onNavigateBack: () -> Unit = {},
    onSavePayment: () -> Unit = {}
) {
    val context = LocalContext.current

    var selectedType by remember { mutableStateOf("CREDITO") }

    var cardNumber by remember { mutableStateOf("") }
    var cardError by remember { mutableStateOf(false) }

    var expiryDate by remember { mutableStateOf("") }
    var dateError by remember { mutableStateOf(false) }

    var cvv by remember { mutableStateOf("") }
    var cvvError by remember { mutableStateOf(false) }

    var cardName by remember { mutableStateOf("") }

    var cpf by remember { mutableStateOf("") }
    var cpfError by remember { mutableStateOf(false) }

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
            Text("Cadastro de Pagamento", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 24.dp))

            Text("Tipo de Cartão", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CardType("Crédito", selectedType == "CREDITO", { selectedType = "CREDITO" }, Modifier.weight(1f))
                CardType("Débito", selectedType == "DEBITO", { selectedType = "DEBITO" }, Modifier.weight(1f))
                CardType("Refeição", selectedType == "REFEICAO", { selectedType = "REFEICAO" }, Modifier.weight(1f))
            }

            Spacer(Modifier.height(24.dp))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                RegistrationTextField(
                    label = "Número do Cartão",
                    placeholder = "Apenas números",
                    value = cardNumber,
                    onValueChange = { input ->
                        if (input.length <= 16) {
                            val clean = input.filter { it.isDigit() }
                            cardNumber = clean
                            cardError = clean.length > 12 && !Validators.isValidCard(clean)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = cardError,
                    subtitle = if (cardError) "Cartão inválido" else null
                )

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(modifier = Modifier.weight(1f)) {
                        RegistrationTextField(
                            label = "Validade (MMAA)",
                            placeholder = "Ex: 1028",
                            value = expiryDate,
                            onValueChange = { input ->
                                if (input.length <= 4) {
                                    val clean = input.filter { it.isDigit() }
                                    expiryDate = clean
                                    dateError = clean.length == 4 && !Validators.isValidExpiryDate(clean)
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = dateError,
                            subtitle = if (dateError) "Data inválida" else null
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        RegistrationTextField(
                            label = "CVV",
                            placeholder = "123",
                            value = cvv,
                            onValueChange = { input ->
                                if (input.length <= 4) {
                                    val clean = input.filter { it.isDigit() }
                                    cvv = clean
                                    cvvError = clean.length !in 0..4
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            isError = cvvError
                        )
                    }
                }

                RegistrationTextField(
                    label = "Nome no Cartão",
                    placeholder = "Como está no cartão",
                    value = cardName,
                    onValueChange = { cardName = it }
                )

                RegistrationTextField(
                    label = "CPF do Titular",
                    placeholder = "Apenas números",
                    value = cpf,
                    onValueChange = { input ->
                        if (input.length <= 11) {
                            val clean = input.filter { it.isDigit() }
                            cpf = clean
                            cpfError = clean.length == 11 && !Validators.isValidCPF(clean)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = cpfError,
                    subtitle = if (cpfError) "CPF inválido" else null
                )
            }

            Spacer(Modifier.weight(1f))
            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    val hasEmptyFields = cardNumber.isBlank() || expiryDate.length != 4 || cvv.length < 3 || cardName.isBlank() || cpf.length != 11
                    val hasValidationErrors = cardError || dateError || cpfError

                    if (hasEmptyFields) {
                        Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    } else if (hasValidationErrors) {
                        Toast.makeText(context, "Corrija os campos em vermelho", Toast.LENGTH_SHORT).show()
                    } else {
                        onSavePayment()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = OrangeColor)
            ) {
                Text("Salvar Cartão", fontSize = 16.sp, color = Color.White)
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentScreenRealtimePreview() {
    TiaLuDeliveryAppTheme {
        PaymentMethodScreen()
    }
}