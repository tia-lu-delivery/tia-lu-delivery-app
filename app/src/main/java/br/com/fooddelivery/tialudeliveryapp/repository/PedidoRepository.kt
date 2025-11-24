package br.com.fooddelivery.tialudeliveryapp.repository

import br.com.fooddelivery.tialudeliveryapp.api.RetrofitClient
import br.com.fooddelivery.tialudeliveryapp.models.*

class PedidoRepository : PedidoDataSource {

    private val api = RetrofitClient.api

    override suspend fun obterEndereco(id: String): Resultado<Endereco> {
        return try {
            Resultado.sucesso(api.obterEndereco(id))
        } catch (e: Exception) {
            Resultado.erro(e.message ?: "Erro ao buscar endereço")
        }
    }

    override suspend fun obterEstabelecimento(id: String): Resultado<Estabelecimento> {
        return try {
            Resultado.sucesso(api.obterEstabelecimento(id))
        } catch (e: Exception) {
            Resultado.erro(e.message ?: "Erro ao buscar estabelecimento")
        }
    }

    override suspend fun obterProdutos(ids: List<String>): Resultado<List<Produto>> {
        return try {
            val request = ProdutoIdsRequest(ids)
            Resultado.sucesso(api.obterProdutos(request))
        } catch (e: Exception) {
            Resultado.erro(e.message ?: "Erro ao buscar produtos")
        }
    }

    override suspend fun enviarPedido(request: PedidoReq): Resultado<PedidoRes> {
        return try {
            Resultado.sucesso(api.enviarPedido(request))
        } catch (e: Exception) {
            Resultado.erro(e.message ?: "Erro ao enviar pedido")
        }
    }
}


