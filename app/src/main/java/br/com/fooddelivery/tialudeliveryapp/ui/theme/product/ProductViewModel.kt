package br.com.fooddelivery.tialudeliveryapp.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class ProductViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState = _uiState.asStateFlow()

    val categoriasDisponiveis = listOf("Lanches", "Pizzas", "Bebidas", "Sobremesas", "Japonês", "Brasileira")

    fun onNomeChange(newVal: String) { _uiState.update { it.copy(nome = newVal) } }
    fun onDescricaoChange(newVal: String) { _uiState.update { it.copy(descricao = newVal) } }
    fun onPrecoChange(newVal: String) { _uiState.update { it.copy(precoUnitario = newVal) } }
    fun onEstoqueChange(newVal: String) { _uiState.update { it.copy(quantidadeEstoque = newVal) } }
    fun onImagemUrlChange(newVal: String) { _uiState.update { it.copy(imagemUrl = newVal) } }

    fun onCategoriaChange(newVal: String) { _uiState.update { it.copy(categoriaSelecionada = newVal) } }
    fun onDisponivelChange(newVal: Boolean) { _uiState.update { it.copy(disponivel = newVal) } }

    fun submitProduct() {
        _uiState.update { it.copy(errorMessage = null) }
        val currentState = _uiState.value

        if (currentState.nome.isBlank() || currentState.descricao.isBlank() ||
            currentState.precoUnitario.isBlank() || currentState.quantidadeEstoque.isBlank() ||
            currentState.categoriaSelecionada.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Preencha todos os campos e selecione uma categoria.") }
            return
        }

        if (ProductRepository.existsByName(currentState.nome)) {
            _uiState.update { it.copy(errorMessage = "Conflito: Já existe um produto com este nome.") }
            return
        }

        val preco = currentState.precoUnitario.replace(",", ".").toDoubleOrNull()
        if (preco == null || preco < 0) {
            _uiState.update { it.copy(errorMessage = "O preço deve ser válido e positivo.") }
            return
        }

        val estoque = currentState.quantidadeEstoque.toIntOrNull()
        if (estoque == null || estoque < 0) {
            _uiState.update { it.copy(errorMessage = "O estoque deve ser um número inteiro positivo.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000)

            val novoProduto = Product(
                id = UUID.randomUUID().toString(),
                nome = currentState.nome,
                preco = preco,
                descricao = currentState.descricao,
                imagemUrl = currentState.imagemUrl,
                categoriaId = currentState.categoriaSelecionada,
                disponivel = currentState.disponivel
            )

            ProductRepository.addProduct(novoProduto)

            _uiState.update { it.copy(isLoading = false, isSuccess = true) }

            delay(500)
            _uiState.update { ProductUiState() }
        }
    }
}