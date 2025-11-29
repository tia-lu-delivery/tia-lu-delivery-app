package br.com.fooddelivery.tialudeliveryapp.data.repository

import br.com.fooddelivery.tialudeliveryapp.data.network.OrderDto
import br.com.fooddelivery.tialudeliveryapp.data.network.OrderItemDto
import br.com.fooddelivery.tialudeliveryapp.data.network.OrdersApi
import br.com.fooddelivery.tialudeliveryapp.model.OrderStatus
import br.com.fooddelivery.tialudeliveryapp.viewmodel.OrdersListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrdersListTest {

    private val dispatcher = StandardTestDispatcher()
    private val api = mockk<OrdersApi>()
    private val repository = OrdersRepositoryImpl(api)
    private lateinit var viewModel: OrdersListViewModel

    @Before
    fun setup() {
        // Configura o Main Dispatcher para testes de coroutines
        kotlinx.coroutines.Dispatchers.setMain(dispatcher)
        viewModel = OrdersListViewModel(repository)
    }

    @After
    fun tearDown() {
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    @Test
    fun `fluxo completo DTO para OrderPresentation com todos os casos`() = runTest {
        val fakeResponse = listOf(
            // 1. CustomerName presente
            OrderDto(
                id = "1",
                customerName = "João",
                userName = null,
                openedAt = "2025-11-25T12:00:00Z",
                items = listOf(OrderItemDto("i1", "Hambúrguer", 2, 20.0)),
                totalPrice = 40.0,
                status = "ACEITO"
            ),
            // 2. CustomerName nulo, userName presente
            OrderDto(
                id = "2",
                customerName = null,
                userName = "Maria",
                openedAt = "2025-11-25T13:00:00Z",
                items = emptyList(),
                totalPrice = 0.0,
                status = "ENTREGUE"
            ),
            // 3. Status desconhecido
            OrderDto(
                id = "3",
                customerName = null,
                userName = null,
                openedAt = "2025-11-25T14:00:00Z",
                items = emptyList(),
                totalPrice = null,
                status = "DESCONHECIDO"
            ),
            // 4. Status nulo
            OrderDto(
                id = "4",
                customerName = "Carlos",
                userName = null,
                openedAt = "invalid-date",
                items = emptyList(),
                totalPrice = 10.0,
                status = null
            )
        )

        coEvery { api.getOrdersByStatus(null) } returns fakeResponse

        // Executa refresh no ViewModel
        viewModel.refresh()

        // Avança todas coroutines pendentes
        advanceUntilIdle()

        val state = viewModel.uiState.first()

        // Devem vir apenas pedidos com status válido
        assertEquals(2, state.orders.size)

        // Pedido 1
        val order1 = state.orders.find { it.id == "1" }!!
        assertEquals("João", order1.customerName)
        assertEquals(OrderStatus.ACEITO, order1.status)
        assertEquals("Aceito", order1.statusLabel)

        // Pedido 2 (fallback de userName)
        val order2 = state.orders.find { it.id == "2" }!!
        assertEquals("Maria", order2.customerName)
        assertEquals(OrderStatus.ENTREGUE, order2.status)
        assertEquals("Entregue", order2.statusLabel)

        // Pedidos 3 e 4 foram filtrados
        assertTrue(state.orders.none { it.id == "3" || it.id == "4" })

        // Verifica a ordenação por data decrescente
        val orderedIds = state.orders.map { it.id }
        assertEquals(listOf("2", "1"), orderedIds)
    }
}
