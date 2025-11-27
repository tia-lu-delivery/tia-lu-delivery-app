package br.com.fooddelivery.tialudeliveryapp.repository

import br.com.fooddelivery.tialudeliveryapp.models.*

interface PedidoDataSource {
    suspend fun obterEndereco(id: String): Resultado<Endereco>
    suspend fun obterEstabelecimento(id: String): Resultado<Estabelecimento>
    suspend fun obterProdutos(estabelecimentoId: String, ids: List<String>): Resultado<List<Produto>>
    suspend fun enviarPedido(request: PedidoReq): Resultado<PedidoRes>
}