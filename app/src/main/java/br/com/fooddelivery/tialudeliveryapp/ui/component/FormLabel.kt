package br.com.fooddelivery.tialudeliveryapp.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fooddelivery.tialudeliveryapp.ui.theme.OrangeGrey80
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme

@Composable
fun FormLabel(modifier: Modifier = Modifier, label: String = "") {
    var value by remember { mutableStateOf("") }

    TextField(
        value = value,
        label = { Text(text = label) },
        onValueChange = { value = it },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = OrangeGrey80,
            focusedContainerColor = OrangeGrey80,
            focusedLabelColor = Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    )
}

@Preview
@Composable
private fun TextInputPreview() {
    TiaLuDeliveryAppTheme {
        FormLabel(label = "Nome do Cardápio")
    }
}