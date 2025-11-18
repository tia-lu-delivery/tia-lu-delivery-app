package br.com.fooddelivery.tialudeliveryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fooddelivery.tialudeliveryapp.ui.screens.payment.PaymentUiState
import br.com.fooddelivery.tialudeliveryapp.ui.utils.Validators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PaymentViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    fun updateCardType(type: String) {
        _uiState.update { it.copy(selectedType = type) }
    }

    fun updateCardNumber(input: String) {
        if (input.length <= 16) {
            val clean = input.filter { it.isDigit() }
            val isError = clean.length > 12 && !Validators.isValidCard(clean)

            _uiState.update {
                it.copy(cardNumber = clean, cardError = isError)
            }
        }
    }

    fun updateExpiryDate(input: String) {
        if (input.length <= 4) {
            val clean = input.filter { it.isDigit() }
            val isError = clean.length == 4 && !Validators.isValidExpiryDate(clean)

            _uiState.update {
                it.copy(expiryDate = clean, dateError = isError)
            }
        }
    }

    fun updateCvv(input: String) {
        if (input.length <= 4) {
            val clean = input.filter { it.isDigit() }
            val isError = clean.length !in 0..4

            _uiState.update {
                it.copy(cvv = clean, cvvError = isError)
            }
        }
    }

    fun updateCardName(input: String) {
        _uiState.update { it.copy(cardName = input) }
    }

    fun updateCpf(input: String) {
        if (input.length <= 11) {
            val clean = input.filter { it.isDigit() }
            val isError = clean.length == 11 && !Validators.isValidCPF(clean)

            _uiState.update {
                it.copy(cpf = clean, cpfError = isError)
            }
        }
    }

    /**
     * Tenta salvar o pagamento.
     * Retorna TRUE se os dados estiverem válidos e prontos para envio.
     * Retorna FALSE se houver erro de validação.
     */
    fun savePayment(): Boolean {
        val state = _uiState.value

        val hasEmptyFields = state.cardNumber.isBlank() || state.expiryDate.length != 4 ||
                state.cvv.length < 3 || state.cardName.isBlank() || state.cpf.length != 11
        val hasValidationErrors = state.cardError || state.dateError || state.cpfError

        return !hasEmptyFields && !hasValidationErrors
    }
}