package br.com.fooddelivery.tialudeliveryapp.models

data class Produto(
    val id: String,
    val idEstabelecimento: String,
    val preco: Double,
    val estoque: Int
)


