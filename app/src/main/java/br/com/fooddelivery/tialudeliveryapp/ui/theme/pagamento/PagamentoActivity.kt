import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme

class PagamentoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TiaLuDeliveryAppTheme {
                TelaPagamentoCompleta()
            }
        }
    }
}
