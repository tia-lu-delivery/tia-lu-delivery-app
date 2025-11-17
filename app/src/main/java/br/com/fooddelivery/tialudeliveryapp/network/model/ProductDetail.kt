package br.com.fooddelivery.tialudeliveryapp.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailRequest (
    val productId: String
)

@Serializable
data class ProductDetailResponse (
    val id_produto: String,
    val nome: String,
    val precoUnitario: Double,
    val descricao: String,
    val quantidadeEstoque: Int,
    val imagemUrl: String,
    val disponivel: Boolean
)