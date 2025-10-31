package br.com.fooddelivery.tialudeliveryapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fooddelivery.tialudeliveryapp.R
import br.com.fooddelivery.tialudeliveryapp.ui.component.BackButton
import br.com.fooddelivery.tialudeliveryapp.ui.component.PrimaryButton
import br.com.fooddelivery.tialudeliveryapp.ui.component.TextInput
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme
import br.com.fooddelivery.tialudeliveryapp.ui.theme.poppins

@Composable
fun RegisterMenuScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BackButton { }

            }

            Text(
                text = "Criar Novo Cardápio",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
            )

            TextInput(
                name = "Nome do cardapio",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Text(
                text = "Ex: Meu jantar, Sobremesas, Especial Verão",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "Descrição (opicional)",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
            )

            TextInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .padding(top = 16.dp)
            )

            Text(
                text = "Adicione itens ao cardápio",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
            )

            PrimaryButton(text = "Salvar", icon = R.drawable.ic_launcher_foreground, modifier = Modifier.fillMaxWidth()) { }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterMenuScreenPreview() {
    TiaLuDeliveryAppTheme {
        RegisterMenuScreen()
    }
}