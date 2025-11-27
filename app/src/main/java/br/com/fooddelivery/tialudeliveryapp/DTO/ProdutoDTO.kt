package br.com.fooddelivery.tialudeliveryapp.DTO

data class ProdutoDTO(
    val id_produto: String,
    val idEstabelecimento: String,
    val nome: String,
    val precoUnitario: Double,
    val descricao: String,
    val quantidadeEstoque: Int,
    val disponivel: Boolean,
    val imagemUrl: String?
)