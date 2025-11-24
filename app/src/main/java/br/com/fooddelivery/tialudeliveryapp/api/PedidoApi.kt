package br.com.fooddelivery.tialudeliveryapp.api

import br.com.fooddelivery.tialudeliveryapp.models.*
import retrofit2.http.*

interface PedidoApi {

    @GET("api/v1/enderecos/{id}")
    suspend fun obterEndereco(
        @Path("id") id: String
    ): Endereco

    @GET("api/v1/estabelecimentos/{id}")
    suspend fun obterEstabelecimento(
        @Path("id") id: String
    ): Estabelecimento

    @POST("api/v1/produtos/by-ids")
    suspend fun obterProdutos(
        @Body body: ProdutoIdsRequest
    ): List<Produto>

    @POST("api/v1/pedidos")
    suspend fun enviarPedido(
        @Body request: PedidoReq
    ): PedidoRes
}
