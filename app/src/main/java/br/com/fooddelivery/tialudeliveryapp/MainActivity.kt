package br.com.fooddelivery.tialudeliveryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TiaLuDeliveryAppTheme  {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val pedidosExemplo = listOf(
                        Pedido("João Silva", "01/11/2025", "Pendente"),
                        Pedido("Maria Santos", "30/10/2025", "Em preparo"),
                        Pedido("Carlos Oliveira", "29/10/2025", "Entregue"),
                        Pedido("Ana Costa", "28/10/2025", "Cancelado")
                    )
                    TelaListaPedidos(pedidos = pedidosExemplo)
                }
            }
        }
    }
}
