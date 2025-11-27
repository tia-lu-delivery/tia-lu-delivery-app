package br.com.fooddelivery.tialudeliveryapp.repository

import br.com.fooddelivery.tialudeliveryapp.models.*
import retrofit2.http.*

interface PedidoApi {

    // Obter endereço do usuário
    @GET("/api/v1/users/address/{id}")
    suspend fun getEndereco(@Path("id") id: String): EnderecoDTO

    // Obter estabelecimento
    @GET("/api/v1/merchant/{id_restaurante}")
    suspend fun getEstabelecimento(@Path("id_restaurante") idRestaurante: String): EstabelecimentoDTO

    // Obter produtos do estabelecimento
    @GET("/api/v1/merchant/{id_restaurante}/products/{id_produto}")
    suspend fun getProduto(
        @Path("id_restaurante") idRestaurante: String,
        @Path("id_produto") idProduto: String
    ): ProdutoDTO

    @GET("/api/v1/merchant/{id_restaurante}/products")
    suspend fun getProdutos(
        @Path("id_restaurante") idRestaurante: String,
        @Query("ids") ids: List<String>
    ): List<ProdutoDTO>

    // Criar pedido
    @POST("/api/v1/orders")
    suspend fun criarPedido(@Body pedido: PedidoReqDTO): PedidoResDTO
}
