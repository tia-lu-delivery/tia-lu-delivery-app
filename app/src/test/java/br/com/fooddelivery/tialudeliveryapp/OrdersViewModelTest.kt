
package br.com.fooddelivery.tialudeliveryapp.viewmodel

import Order
import OrdersRepository
import OrdersViewModel
import android.os.Build
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

class FakeRepo : OrdersRepository {
    override suspend fun fetchOrders(): List<Order> {
        return listOf(
            Order("1","Joao","2024-07-09T18:00:00-03:00", OrderStatus.FAZENDO),
            Order("2","Maria","2024-07-10T12:00:00-03:00", OrderStatus.ACEITO)
        )
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@androidx.test.filters.SdkSuppress(minSdkVersion = Build.VERSION_CODES.O)
class OrdersViewModelTest {

    private val repo = FakeRepo()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `refresh carrega lista e retorna ordenado por data`() = runTest {
        val vm = OrdersViewModel(FakeRepo())
        testDispatcher.scheduler.advanceUntilIdle()
        val state = vm.uiState.value
        assert(state.orders.isNotEmpty())
        assert(state.orders.first().id == "2") // Maria vem primeiro, 10/07
    }

    @Test
    fun `setFilter filtra somente os de um status`() = runTest {
        val vm = OrdersViewModel(FakeRepo())
        testDispatcher.scheduler.advanceUntilIdle()

        vm.setFilter(OrderStatus.ACEITO)
        testDispatcher.scheduler.advanceUntilIdle() // precisa avançar novamente para o combine
        val state = vm.uiState.value

        assert(state.orders.size == 1)
        assert(state.orders.first().id == "2")
    }
}
