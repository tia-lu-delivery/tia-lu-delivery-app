package br.com.fooddelivery.tialudeliveryapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterMenuViewModel : ViewModel() {

    private val _menuName = MutableStateFlow("")

    val menuName: StateFlow<String> = _menuName.asStateFlow()

    fun updateMenuName(name: String) {
        _menuName.value = name
    }

    fun saveMenu() {
        // implementar chamada de API -- Cesar
        val name = _menuName.value

        if (name.isNotBlank()) {
            println("Salvando cardápio: $name")
        }
    }
}