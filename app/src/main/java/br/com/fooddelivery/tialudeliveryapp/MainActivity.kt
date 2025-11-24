package br.com.fooddelivery.tialudeliveryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import br.com.fooddelivery.tialudeliveryapp.data.network.RetrofitClient
import br.com.fooddelivery.tialudeliveryapp.data.repository.OrdersRepositoryImpl
import br.com.fooddelivery.tialudeliveryapp.ui.TelaListaPedidos
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme
import br.com.fooddelivery.tialudeliveryapp.viewmodel.OrdersViewModel
import br.com.fooddelivery.tialudeliveryapp.viewmodel.OrdersViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModelFactory by lazy {
        OrdersViewModelFactory(OrdersRepositoryImpl(RetrofitClient.ordersApi))
    }

    private val viewModel: OrdersViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TiaLuDeliveryAppTheme {
                val uiState by viewModel.uiState.collectAsState()

                TelaListaPedidos(
                    orders = uiState.orders,
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage,
                    availableFilters = viewModel.availableFilters,
                    activeFilter = uiState.activeFilter,
                    onFilterChange = { viewModel.setFilter(it) },
                    onRetry = { viewModel.retry() }
                )
            }
        }
    }
}
