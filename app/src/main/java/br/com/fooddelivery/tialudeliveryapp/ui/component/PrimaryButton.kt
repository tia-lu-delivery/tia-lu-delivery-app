package br.com.fooddelivery.tialudeliveryapp.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fooddelivery.tialudeliveryapp.R
import br.com.fooddelivery.tialudeliveryapp.ui.theme.Orange80
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme
import br.com.fooddelivery.tialudeliveryapp.ui.theme.Typography

@Composable
fun PrimaryButton(modifier: Modifier = Modifier, text: String, icon: Int, onClick: () -> Unit) {
    ElevatedButton(
        modifier = modifier.height(50.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Orange80,
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
            )
            Text(
                text = text,
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    TiaLuDeliveryAppTheme {
        PrimaryButton(text = "Teste", icon = R.drawable.ic_launcher_foreground) { }
    }
}