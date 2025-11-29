package br.com.fooddelivery.tialudeliveryapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fooddelivery.tialudeliveryapp.ui.components.PageIndicator
import br.com.fooddelivery.tialudeliveryapp.ui.components.RegistrationTextField
import br.com.fooddelivery.tialudeliveryapp.ui.theme.OrangeColor
import br.com.fooddelivery.tialudeliveryapp.ui.theme.ScreenBgColor
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartnerDetailsScreen() {
    var cpf by remember { mutableStateOf("") }
    var rg by remember { mutableStateOf("") }
    var orgaoEmissor by remember { mutableStateOf("") }
    var nomeCompleto by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        containerColor = ScreenBgColor,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick ={}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Dados do Sócio Proprietário",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                RegistrationTextField(
                    label = "CPF",
                    placeholder = "",
                    value = cpf,
                    onValueChange = { cpf = it },
                )

                RegistrationTextField(
                    label = "RG",
                    placeholder = "",
                    value = rg,
                    onValueChange = { rg = it }
                )

                RegistrationTextField(
                    label = "Órgo Emissor do RG",
                    placeholder = "SSP/SP",
                    value = orgaoEmissor,
                    onValueChange = { orgaoEmissor = it }
                )

                RegistrationTextField(
                    label = "Nome Completo",
                    placeholder = "Tia Lu",
                    value = nomeCompleto,
                    onValueChange = { nomeCompleto = it }
                )

                RegistrationTextField(
                    label = "E-mail",
                    placeholder = "tialudelivery@tialu.com",
                    value = email,
                    onValueChange = { email = it }
                )

                RegistrationTextField(
                    label = "Telefone",
                    placeholder = "Telefone (WhatsApp)",
                    value = email,
                    onValueChange = { email = it }
                )
            }

            Spacer(Modifier.weight(1f))
            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = OrangeColor)
            ) {
                Text(
                    text = "Finalizar cadastro",
                    fontSize = 16.sp
                )
            }

            Spacer(Modifier.height(16.dp))

            PageIndicator(
                pageCount = 2,
                currentPage = 1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PartnerDetailsPreview() {
    TiaLuDeliveryAppTheme {
        PartnerDetailsScreen()
    }
}