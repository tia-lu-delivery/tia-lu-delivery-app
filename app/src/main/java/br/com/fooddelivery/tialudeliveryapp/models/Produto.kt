package br.com.fooddelivery.tialudeliveryapp.models

data class Produto(
    val id: String,
    val idEstabelecimento: String,
    val nome: String,
    val preco: Double,
    val descricao: String,
    val estoque: Int,
    val disponivel: Boolean,
    val imagemUrl: String?
)


