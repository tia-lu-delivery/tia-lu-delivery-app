package br.com.fooddelivery.tialudeliveryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.fooddelivery.tialudeliveryapp.repository.FakeRegistroRepository
import br.com.fooddelivery.tialudeliveryapp.ui.TelaNovoPedido
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme
import br.com.fooddelivery.tialudeliveryapp.viewmodel.PedidoRegistroViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TiaLuDeliveryAppTheme {
                // Instancia fake repo e ViewModel
                val fakeRepo = remember { FakeRegistroRepository(currentUserId = "user123") }
                val pedidoViewModel = remember { PedidoRegistroViewModel(fakeRepo, "user123") }

                Surface(modifier = Modifier.fillMaxSize()) {
                    TelaNovoPedido(
                        viewModel = pedidoViewModel,
                        titulo = "Novo Pedido",
                        onBack = { finish() }, // volta para a tela anterior (fecha app neste caso)
                        onPedidoCriado = { pedidoId ->
                            // Apenas loga no console por enquanto
                            println("Pedido criado com sucesso: $pedidoId")
                        }
                    )
                }
            }
        }
    }
}
