package br.com.fooddelivery.tialudeliveryapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fooddelivery.tialudeliveryapp.ui.theme.BorderDefaultColor
import br.com.fooddelivery.tialudeliveryapp.ui.theme.BorderErrorColor
import br.com.fooddelivery.tialudeliveryapp.ui.theme.PlaceholderColor

@Composable
fun RegistrationTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    subtitle: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false
) {
    val borderColor = if (isError) BorderErrorColor else BorderDefaultColor

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = BorderStroke(1.dp, borderColor),
        shadowElevation = 2.dp
    ) {
        Column(
            Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color.Black
            )

            if (subtitle != null) {
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = if (isError) Color.Red else PlaceholderColor
                )
            }

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                keyboardOptions = keyboardOptions,
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(top = 4.dp)) {
                        if (value.isEmpty() && placeholder.isNotEmpty()) {
                            Text(
                                text = placeholder,
                                fontSize = 16.sp,
                                color = PlaceholderColor
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}