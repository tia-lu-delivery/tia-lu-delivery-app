package br.com.fooddelivery.tialudeliveryapp.repository

import br.com.fooddelivery.tialudeliveryapp.models.*

interface PedidoDataSource {

    /**
     * Obtém os dados de um endereço salvo pelo ID.
     */
    suspend fun obterEndereco(id: String): Resultado<Endereco>

    /**
     * Obtém os dados de um estabelecimento pelo ID.
     */
    suspend fun obterEstabelecimento(id: String): Resultado<Estabelecimento>

    /**
     * Obtém a lista de produtos a partir dos IDs enviados no pedido.
     */
    suspend fun obterProdutos(ids: List<String>): Resultado<List<Produto>>

    /**
     * Envia o pedido completo para o backend.
     */
    suspend fun enviarPedido(request: PedidoReq): Resultado<PedidoRes>
}
