package br.com.fooddelivery.tialudeliveryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme

class MainActivity : ComponentActivity() {

    // ViewModel criado com Factory que injeta o Repository
    private val ordersViewModel: OrdersViewModel by viewModels {
        OrdersViewModelFactory(OrdersRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TiaLuDeliveryAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Observar o uiState exposto pelo ViewModel
                    val uiState by ordersViewModel.uiState.collectAsState()

                    TelaListaPedidos(
                        uiState = uiState,
                        availableFilters = ordersViewModel.availableFilters,
                        onSelectFilter = { status -> ordersViewModel.setFilter(status) },
                        onRetry = { ordersViewModel.refresh() }
                    )
                }
            }
        }
    }
}
