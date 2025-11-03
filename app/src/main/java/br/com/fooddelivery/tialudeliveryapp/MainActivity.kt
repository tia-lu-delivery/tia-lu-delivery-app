package br.com.fooddelivery.tialudeliveryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import br.com.fooddelivery.tialudeliveryapp.screens.OrdersScreen
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme
import br.com.fooddelivery.tialudeliveryapp.viewmodel.OrdersViewModel

class MainActivity : ComponentActivity() {

    private val ordersViewModel: OrdersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TiaLuDeliveryAppTheme {
                OrdersContent(viewModel = ordersViewModel)
            }
        }
    }
}

@Composable
fun OrdersContent(viewModel: OrdersViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getOrders("PENDENTE")
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        OrdersScreen(viewModel = viewModel)
    }
}