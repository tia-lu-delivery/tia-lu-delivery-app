package br.com.fooddelivery.tialudeliveryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.fooddelivery.tialudeliveryapp.presentation.screens.PendingOrdersScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PendingOrdersScreen()
        }
    }
}

