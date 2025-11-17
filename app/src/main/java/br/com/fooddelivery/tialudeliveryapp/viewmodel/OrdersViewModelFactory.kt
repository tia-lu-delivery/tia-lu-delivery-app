import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fooddelivery.tialudeliveryapp.data.repository.OrdersRepository


class OrdersViewModelFactory(
    private val repository: OrdersRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            return OrdersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
