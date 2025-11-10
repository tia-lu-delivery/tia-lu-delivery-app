package br.com.fooddelivery.tialudeliveryapp

import br.com.fooddelivery.tialudeliveryapp.domain.OrderStatus
import org.junit.Assert.assertEquals
import org.junit.Test

class OrdersRepositoryImplTest {

    private val repo = OrdersRepositoryImpl(api = FakeOrdersApi())

    @Test
    fun `mapStatusFromApi deve mapear corretamente variações de status`() {
        assertEquals(OrderStatus.OPEN, repo.mapStatusFromApi("aberto"))
        assertEquals(OrderStatus.OPEN, repo.mapStatusFromApi("Open"))
        assertEquals(OrderStatus.PREPARING, repo.mapStatusFromApi("em preparo"))
        assertEquals(OrderStatus.DELIVERED, repo.mapStatusFromApi("Entregue"))
        assertEquals(OrderStatus.UNKNOWN, repo.mapStatusFromApi("cancelado"))
    }
}