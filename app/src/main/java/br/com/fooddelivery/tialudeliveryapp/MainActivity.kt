package br.com.fooddelivery.tialudeliveryapp

import OrdersViewModel
import OrdersViewModelFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fooddelivery.tialudeliveryapp.data.repository.OrdersRepositoryImpl
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme
import br.com.fooddelivery.tialudeliveryapp.viewmodel.OrdersViewModel
import br.com.fooddelivery.tialudeliveryapp.viewmodel.OrdersViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TiaLuDeliveryAppTheme {


                val repository = OrdersRepositoryImpl()

                val factory = OrdersViewModelFactory(repository)

                val ordersViewModel: OrdersViewModel = viewModel(factory = factory)


                LaunchedEffect(Unit) {
                    ordersViewModel.loadOrders()
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TiaLuDeliveryAppTheme {
        Greeting("Android")
    }
}
