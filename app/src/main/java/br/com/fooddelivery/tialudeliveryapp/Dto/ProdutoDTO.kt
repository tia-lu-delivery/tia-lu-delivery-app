package br.com.fooddelivery.tialudeliveryapp.Dto

data class ProdutoDTO(
    val id: String,
    val idEstabelecimento: String,
    val preco: Double,
    val estoque: Int
)