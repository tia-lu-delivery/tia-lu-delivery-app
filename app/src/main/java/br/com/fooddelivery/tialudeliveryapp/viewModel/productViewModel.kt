package br.com.fooddelivery.tialudeliveryapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ProductViewModel : ViewModel() {

    var name by mutableStateOf("")
        private set

    var price by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var pdvCode by mutableStateOf("")
        private set

    fun onNameChange(newValue: String) {
        name = newValue
    }

    fun onPriceChange(newValue: String) {
        price = newValue
    }

    fun onDescriptionChange(newValue: String) {
        description = newValue
    }

    fun onPdvCodeChange(newValue: String) {
        pdvCode = newValue
    }

    fun clearFields() {
        name = ""
        price = ""
        description = ""
        pdvCode = ""
    }
}
