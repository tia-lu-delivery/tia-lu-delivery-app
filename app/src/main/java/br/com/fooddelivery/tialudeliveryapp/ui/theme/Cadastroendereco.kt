package br.com.fooddelivery.tialudeliveryapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
@Composable
fun CadastroEnderecoScreen(
    onSalvar: (String, String, String, String, String, String) -> Unit
) {
    val laranja = Color(0xFFFF9800)
    val branco = Color.White

    var rua by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(branco)
    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFFFFA726),
                            Color(0xFFFF9800)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Cadastro de Endereço",
                color = branco,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CampoTextoIcone("Rua", rua, { rua = it }, Icons.Default.Home)
            CampoTextoIcone("Número", numero, { numero = it }, Icons.Default.Pin)
            CampoTextoIcone("Bairro", bairro, { bairro = it }, Icons.Default.LocationCity)
            CampoTextoIcone("Cidade", cidade, { cidade = it }, Icons.Default.Place)
            CampoTextoIcone("Estado", estado, { estado = it }, Icons.Default.Flag)
            CampoTextoIcone("CEP", cep, { cep = it }, Icons.Default.LocalPostOffice)

            Spacer(modifier = Modifier.height(26.dp))


            Button(
                onClick = {
                    onSalvar(rua, numero, bairro, cidade, estado, cep)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = laranja,
                    contentColor = branco
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(
                    "Salvar Endereço",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun CampoTextoIcone(
    label: String,
    valor: String,
    onChange: (String) -> Unit,
    icone: androidx.compose.ui.graphics.vector.ImageVector
) {
    val laranja = Color(0xFFFF9800)

    OutlinedTextField(
        value = valor,
        onValueChange = onChange,
        label = { Text(label) },
        leadingIcon = { Icon(icone, contentDescription = label, tint = laranja) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = laranja,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = laranja,
            focusedLabelColor = laranja
        )
    )
}
@Preview(showBackground = true)
@Composable
fun PreviewCadastroEnderecoScreen() = CadastroEnderecoScreen(onSalvar = { _, _, _, _, _, _ -> })



