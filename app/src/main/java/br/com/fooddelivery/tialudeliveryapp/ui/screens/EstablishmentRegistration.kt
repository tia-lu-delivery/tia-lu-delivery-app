package br.com.fooddelivery.tialudeliveryapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun RegistrationScreen() {
    var razaoSocial by remember { mutableStateOf("") }
    var nomeFantasia by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var inscricaoEstadual by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }

    Scaffold(
        containerColor = ScreenBgColor,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { }) {
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
                text = "Cadastro do Estabelecimento",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                RegistrationTextField(
                    label = "Razão Social",
                    placeholder = "Nome do seu estabelecimento",
                    value = razaoSocial,
                    onValueChange = { razaoSocial = it },
                )

                RegistrationTextField(
                    label = "Nome Fantasia",
                    placeholder = "",
                    value = nomeFantasia,
                    onValueChange = { nomeFantasia = it }
                )

                RegistrationTextField(
                    label = "CNPJ",
                    placeholder = "",
                    value = cnpj,
                    onValueChange = { cnpj = it }
                )

                RegistrationTextField(
                    label = "Inscrição Estadual (IE)",
                    placeholder = "",
                    value = inscricaoEstadual,
                    onValueChange = { inscricaoEstadual = it }
                )

                Text(
                    text = "Endereço do estabelecimento",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                RegistrationTextField(
                    label = "CEP",
                    placeholder = "",
                    value = cep,
                    onValueChange = { cep = it }
                )

                RegistrationTextField(
                    label = "Endereço",
                    placeholder = "",
                    value = endereco,
                    onValueChange = { endereco = it }
                )

                RegistrationTextField(
                    label = "Bairro",
                    placeholder = "",
                    value = bairro,
                    onValueChange = { bairro = it }
                )

                RegistrationTextField(
                    label = "Cidade",
                    placeholder = "",
                    value = cidade,
                    onValueChange = { cidade = it }
                )

                RegistrationTextField(
                    label = "Estado",
                    placeholder = "UF",
                    value = estado,
                    onValueChange = { estado = it }
                )
            }

            Spacer(Modifier.weight(1f))
            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = OrangeColor)
            ) {
                Text(
                    text = "Avançar",
                    fontSize = 16.sp
                )
            }

            Spacer(Modifier.height(16.dp))

            PageIndicator(
                pageCount = 2,
                currentPage = 0,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TiaLuDeliveryAppTheme {
        RegistrationScreen()
    }
}