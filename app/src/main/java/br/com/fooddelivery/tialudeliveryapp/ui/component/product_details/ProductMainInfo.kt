package br.com.fooddelivery.tialudeliveryapp.ui.component.product_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme
import br.com.fooddelivery.tialudeliveryapp.ui.theme.poppins

@Composable
fun ProductMainInfo(
    modifier: Modifier = Modifier,
    productName: String,
    productDescription: String,
    productQuantity: Int,
    productPrice: Double
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = productName,
            fontFamily = poppins,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = productDescription,
            fontFamily = poppins,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Preço")
                Text(text = "R$$productPrice")
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Quantidade Disponível")
                Text(text = "$productQuantity")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductMainInfoPreview() {
    TiaLuDeliveryAppTheme() {
        ProductMainInfo(
            productName = "X-Burger Clássico",
            productDescription =  "Hambúrguer de 180g, queijo cheddar, alface, tomate e maionese especial no pão brioche.",
            productQuantity = 20,
            productPrice = 11.50
        )
    }
}