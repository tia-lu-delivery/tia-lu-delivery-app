package br.com.fooddelivery.tialudeliveryapp.repository

import br.com.fooddelivery.tialudeliveryapp.models.*
import br.com.fooddelivery.tialudeliveryapp.DTO.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import br.com.fooddelivery.tialudeliveryapp.mappers.*
import br.com.fooddelivery.tialudeliveryapp.repository.PedidoApi
import br.com.fooddelivery.tialudeliveryapp.repository.PedidoDataSource

class PedidoRepository(private val api: PedidoApi) : PedidoDataSource {

    override suspend fun obterEndereco(id: String): Resultado<Endereco> = withContext(Dispatchers.IO) {
        try {
            val resp = api.getEndereco(id)
            Resultado.Sucesso(resp.toDomain())
        } catch (e: Exception) {
            Resultado.Erro("Endereço não encontrado")
        }
    }




    override suspend fun obterEstabelecimento(id: String): Resultado<Estabelecimento> = withContext(Dispatchers.IO) {
        try {
            val resp = api.getEstabelecimento(id)
            Resultado.Sucesso(resp.toDomain())
        } catch (e: Exception) {
            Resultado.Erro("Estabelecimento não encontrado")
        }
    }

    override suspend fun obterProdutos(estabelecimentoId: String, ids: List<String>): Resultado<List<Produto>> =
        withContext(Dispatchers.IO) {
            try {
                val resp = api.getProdutos(estabelecimentoId, ids)
                Resultado.Sucesso(resp.map { it.toDomain() })
            } catch (e: Exception) {
                Resultado.Erro("Produtos não encontrados")
            }
        }

    override suspend fun enviarPedido(request: PedidoReq): Resultado<PedidoRes> = withContext(Dispatchers.IO) {
        try {
            val dto = request.toDTO()
            val resp = api.criarPedido(dto)
            Resultado.Sucesso(resp.toDomain())
        } catch (e: Exception) {
            Resultado.Erro("Falha ao criar pedido: ${e.message}")
        }
    }
}
