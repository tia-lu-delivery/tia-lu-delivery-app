package br.com.fooddelivery.tialudeliveryapp.ui.product

data class ProductUiState(
    val nome: String = "",
    val descricao: String = "",
    val precoUnitario: String = "",
    val quantidadeEstoque: String = "",
    val imagemUrl: String = "",

    val categoriaSelecionada: String = "",
    val disponivel: Boolean = true,

    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)