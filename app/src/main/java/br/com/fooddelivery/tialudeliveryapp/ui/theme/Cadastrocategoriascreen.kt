package br.com.fooddelivery.tialudeliveryapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroCategoriaScreen(
    onSalvar: (String, String) -> Unit = { _, _ -> },
    onVoltar: () -> Unit = {}
) {
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Categoria", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFFDFDFD))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFFFFA726), shape = RoundedCornerShape(50.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Category,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Cadastre uma nova categoria",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome da Categoria") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

         
            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(32.dp))


            Button(
                onClick = {
                    if (nome.isNotBlank()) {
                        onSalvar(nome, descricao)
                        mensagem = "Categoria '$nome' cadastrada com sucesso!"
                        nome = ""
                        descricao = ""
                    } else {
                        mensagem = " O nome da categoria é obrigatório."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Salvar Categoria",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (mensagem.isNotEmpty()) {
                Text(
                    text = mensagem,
                    color = if (mensagem.contains("sucesso")) Color(0xFF43A047) else Color.Red,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCadastroCategoriaScreen() {
    CadastroCategoriaScreen()
}
