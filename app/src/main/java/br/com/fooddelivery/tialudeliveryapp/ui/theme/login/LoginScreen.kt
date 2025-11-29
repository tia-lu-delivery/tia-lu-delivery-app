package br.com.fooddelivery.tialudeliveryapp.ui.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Fastfood
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

private val BrandOrange = Color(0xFFFF9F1C)
private val BrandTextDark = Color(0xFF1A1A1A)
private val BrandCreamBg = Color(0xFFFFFBF2)
private val InputGrayBg = Color(0xFFF5F5F5)

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            val msg = if (uiState.isRegisterMode) "Cadastro realizado com sucesso!" else "Login realizado!"
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandCreamBg)
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Fastfood,
                contentDescription = null,
                tint = BrandOrange,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Tia Lu Delivery",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = BrandOrange
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = if (uiState.isRegisterMode) "Crie sua conta" else "Olá, Cliente",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = BrandTextDark
        )

        Text(
            text = if (uiState.isRegisterMode) "Preencha seus dados completos" else "Faça login para pedir seu rango.",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        if (uiState.isRegisterMode) {
            CustomTextField(value = viewModel.nome, onValueChange = { viewModel.onNomeChange(it) }, label = "Nome")
            CustomTextField(value = viewModel.sobrenome, onValueChange = { viewModel.onSobrenomeChange(it) }, label = "Sobrenome")
            CustomTextField(value = viewModel.telefone, onValueChange = { viewModel.onTelefoneChange(it) }, label = "Telefone", keyboardType = KeyboardType.Phone)

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f)) {
                    CustomTextField(value = viewModel.cep, onValueChange = { viewModel.onCepChange(it) }, label = "CEP", keyboardType = KeyboardType.Number)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.weight(1f)) {
                    CustomTextField(value = viewModel.numeroCasa, onValueChange = { viewModel.onNumeroCasaChange(it) }, label = "Nº Casa", keyboardType = KeyboardType.Number)
                }
            }

            CustomTextField(value = viewModel.endereco, onValueChange = { viewModel.onEnderecoChange(it) }, label = "Endereço")
            CustomTextField(value = viewModel.bairro, onValueChange = { viewModel.onBairroChange(it) }, label = "Bairro")
            CustomTextField(value = viewModel.complemento, onValueChange = { viewModel.onComplementoChange(it) }, label = "Complemento (Opcional)")
        }

        CustomTextField(
            value = viewModel.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = "Email",
            keyboardType = KeyboardType.Email
        )

        CustomTextField(
            value = viewModel.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = "Senha",
            isPassword = true
        )

        if (uiState.error != null) {
            Text(
                text = uiState.error!!,
                color = Color.Red,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.doAction() },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = BrandOrange),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(
                    text = if (uiState.isRegisterMode) "Confirmar Cadastro" else "Entrar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        ) {
            Text(
                text = if (uiState.isRegisterMode) "Já tem conta? " else "Não tem conta? ",
                color = Color.Gray
            )
            Text(
                text = if (uiState.isRegisterMode) "Fazer Login" else "Cadastre-se",
                color = BrandOrange,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { viewModel.toggleLoginMode() }
            )
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BrandOrange,
            focusedLabelColor = BrandOrange,
            cursorColor = BrandOrange,
            unfocusedContainerColor = InputGrayBg,
            focusedContainerColor = Color.White,
            unfocusedBorderColor = Color.Transparent
        ),
        singleLine = true
    )
}