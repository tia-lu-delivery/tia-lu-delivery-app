package br.com.fooddelivery.tialudeliveryapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fooddelivery.tialudeliveryapp.data.repository.ktor_repository.KtorProductRepository
import br.com.fooddelivery.tialudeliveryapp.network.model.ProductDetailResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailScreenViewModel: ViewModel() {

    private val defaultValue = ProductDetailResponse(
        productId = "0",
        name = "",
        description = "",
        price = 0.0,
        imageUrl = "",
        quantity = 0,
        isAvailable = true
    )

    private val _product = MutableStateFlow<Result<ProductDetailResponse>>(Result.success(defaultValue))
    val product: StateFlow<Result<ProductDetailResponse>> = _product.asStateFlow()

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            try {
                val product = KtorProductRepository().getProductById(productId)
                _product.value = product.mapCatching {
                    it.copy(
                        productId = it.productId,
                        name = it.name,
                        price = it.price,
                        description = it.description,
                        quantity = it.quantity,
                        imageUrl = it.imageUrl,
                        isAvailable = it.isAvailable
                    )
                }
            }catch (e: Exception){
                e.printStackTrace()
                _product.value = Result.failure(e)
            }
        }
    }
}