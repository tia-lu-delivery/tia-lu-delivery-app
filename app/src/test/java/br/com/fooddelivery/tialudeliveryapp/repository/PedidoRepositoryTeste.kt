
package br.com.fooddelivery.tialudeliveryapp.repository

import br.com.fooddelivery.tialudeliveryapp.models.PedidoReq
import br.com.fooddelivery.tialudeliveryapp.models.PedidoItemReq
import br.com.fooddelivery.tialudeliveryapp.models.PedidoRes
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response

class PedidoRepositoryTest {

    private val api = mock(PedidoApi::class.java)
    private val repository = PedidoRepository(api)

    @Test
    fun `deve retornar erro ADDRESS_NOT_FOUND`() = runBlocking {
        val erroJson = """
            {
              "codigoErro": "ADDRESS_NOT_FOUND",
              "mensagem": "O ID de endereço de entrega 'end987654321' não foi encontrado ou não pertence à sua conta.",
              "acaoSugerida": "Verifique se o endereço ainda está salvo em sua carteira ou utilize um ID válido."
            }
        """.trimIndent()

        val errorResponse = Response.error<PedidoRes>(
            404,
            ResponseBody.create("application/json".toMediaType(), erroJson)
        )

        val payload = gerarPayloadPedido()

        `when`(api.criarPedido(payload)).thenReturn(errorResponse)

        val resposta = repository.criarPedido(payload)

        assertFalse(resposta.sucesso)
        assertEquals("ADDRESS_NOT_FOUND", resposta.codigoErro)
    }


    @Test
    fun `deve retornar erro DELIVERY_OUT_OF_AREA`() = runBlocking {
        val erroJson = """
            {
              "codigoErro": "DELIVERY_OUT_OF_AREA",
              "mensagem": "O estabelecimento não realiza entregas para o endereço vinculado ao ID 'end987654321'. Tente outro endereço salvo.",
              "detalhes": {
                "estabelecimento": "Exemplo Fantasia",
                "cepDoEndereco": "01001000"
              }
            }
        """.trimIndent()

        val errorResponse = Response.error<PedidoRes>(
            400,
            ResponseBody.create("application/json".toMediaType(), erroJson)
        )

        val payload = gerarPayloadPedido()

        `when`(api.criarPedido(payload)).thenReturn(errorResponse)

        val resposta = repository.criarPedido(payload)

        assertFalse(resposta.sucesso)
        assertEquals("DELIVERY_OUT_OF_AREA", resposta.codigoErro)
    }


    /**
     * Função auxiliar para montar o payload corretamente,
     * usando seus modelos reais: PedidoReq e PedidoItemReq.
     */
    private fun gerarPayloadPedido(): PedidoReq {
        return PedidoReq(
            idEstabelecimento = "a1b2c3d4e5f6g7h8",
            valorTotalEnviado = 65.70,
            observacoesGerais = "Sem picles no X-Burger.",
            idEnderecoEntrega = "end987654321",
            itens = listOf(
                PedidoItemReq(
                    idProduto = "p1r2o3d4u5t6o7",
                    quantidade = 2,
                    precoUnitarioMomentoCompra = 25.90
                ),
                PedidoItemReq(
                    idProduto = "p1r2o3d4u5t6o8",
                    quantidade = 1,
                    precoUnitarioMomentoCompra = 18.00
                )
            ),
            desconto = null // se existir modelo de Desconto, posso incluir
        )
    }
}
