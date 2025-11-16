package br.com.fooddelivery.tialudeliveryapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.fooddelivery.tialudeliveryapp.network.KtorHttpClient
import br.com.fooddelivery.tialudeliveryapp.network.model.CreateMenuRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterMenuViewModel : ViewModel() {

    private val _menuName = MutableStateFlow("")

    val menuName: StateFlow<String> = _menuName.asStateFlow()

    fun updateMenuName(name: String) {
        _menuName.value = name
    }

    suspend fun saveMenu() {

        val name = _menuName.value

        if (name.isNotBlank()) {
            KtorHttpClient.postCreateMenu(data = CreateMenuRequest(name = name))
        }
    }
}