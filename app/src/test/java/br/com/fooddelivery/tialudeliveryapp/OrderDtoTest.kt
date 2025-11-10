package br.com.fooddelivery.tialudeliveryapp

import br.com.fooddelivery.tialudeliveryapp.data.network.OrderDto
import com.google.gson.Gson

/**
 * Testes unitários para verificar a desserialização do OrderDto a partir de JSON.
 */
class OrderDtoTest {

    @Test
    fun `deve desserializar JSON corretamente`() {
        val json = """
            {
                "id": "123",
                "userName": "Maria",
                "openedAt": "2025-11-09T18:00:00Z",
                "status": "aberto"
            }
        """.trimIndent()

        val dto = Gson().fromJson(json, OrderDto::class.java)

        assertEquals("123", dto.id)
        assertEquals("Maria", dto.userName)
        assertEquals("2025-11-09T18:00:00Z", dto.openedAt)
        assertEquals("aberto", dto.status)
    }
}