package br.com.fooddelivery.tialudeliveryapp.ui.product

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Product(
    val id: String,
    val nome: String,
    val preco: Double,
    val descricao: String,
    val imagemUrl: String,
    val categoriaId: String,
    val disponivel: Boolean
)

object ProductRepository {
    private val _products = MutableStateFlow<List<Product>>(
        listOf(
            Product("1", "X-Burger Especial", 25.90, "Delicioso burger", "", "Lanches", true),
            Product("2", "Pizza Calabresa", 45.00, "Queijo e calabresa", "", "Pizzas", true),
            Product("3", "Sushi Combo", 60.00, "12 peças variadas", "", "Japonês", false)
        )
    )
    val products = _products.asStateFlow()

    fun addProduct(product: Product) {
        _products.update { currentList ->
            currentList + product
        }
    }

    fun existsByName(name: String): Boolean {
        return _products.value.any { it.nome.equals(name, ignoreCase = true) }
    }
}