package br.com.fooddelivery.tialudeliveryapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fooddelivery.tialudeliveryapp.network.KtorHttpClient
import br.com.fooddelivery.tialudeliveryapp.network.model.CreateMenuRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterMenuViewModel : ViewModel() {

    private val _menuName = MutableStateFlow("")

    val menuName: StateFlow<String> = _menuName.asStateFlow()

    fun updateMenuName(name: String) {
        _menuName.value = name
    }

    fun saveMenu() {
        val name = _menuName.value

        if (name.isBlank()) {
            return
        }

        viewModelScope.launch {
          KtorHttpClient.postCreateMenu(data = CreateMenuRequest(name = name))
        }
    }
}