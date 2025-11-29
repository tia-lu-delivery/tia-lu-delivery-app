package br.com.fooddelivery.tialudeliveryapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fooddelivery.tialudeliveryapp.ui.theme.OrangeColor

@Composable
fun CardType(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) OrangeColor else Color.Transparent,
            contentColor = if (selected) Color.White else OrangeColor
        ),
        border = BorderStroke(1.dp, OrangeColor)
    ) {
        Text(text, fontSize = 12.sp, fontWeight = if(selected) FontWeight.Bold else FontWeight.Normal)
    }
}