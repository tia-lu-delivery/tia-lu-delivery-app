package br.com.fooddelivery.tialudeliveryapp.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailRequest (
    val productId: String
)

@Serializable
data class ProductDetailResponse (
    @SerialName("id_produto")
    val productId: String,
    @SerialName("nome")
    val name: String,
    @SerialName("precoUnitario")
    val price: Double,
    @SerialName("descricao")
    val description: String,
    @SerialName("quantidadeEstoque")
    val quantity: Int,
    @SerialName("imagemUrl")
    val imageUrl: String,
    @SerialName("disponivel")
    val isAvailable: Boolean
)