package br.com.fooddelivery.tialudeliveryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fooddelivery.tialudeliveryapp.data.ErrorResponse
import br.com.fooddelivery.tialudeliveryapp.data.PaymentRequest
import br.com.fooddelivery.tialudeliveryapp.data.RetrofitClient
import br.com.fooddelivery.tialudeliveryapp.ui.screens.payment.PaymentUiState
import br.com.fooddelivery.tialudeliveryapp.ui.utils.Validators
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    private val _apiError = MutableStateFlow<String?>(null)
    val apiError: StateFlow<String?> = _apiError.asStateFlow()

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
            val isError = clean.length !in 3..4

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

    fun savePayment(userId: String, onSuccess: () -> Unit) {
        val state = _uiState.value
        val mes = state.expiryDate.take(2).toIntOrNull() ?: return
        val anoShort = state.expiryDate.takeLast(2)
        val anoFull = "20$anoShort".toIntOrNull() ?: return

        val request = PaymentRequest(
            numeroCartao = state.cardNumber,
            validadeMes = mes,
            validadeAno = anoFull,
            cvv = state.cvv,
            nomeTitular = state.cardName,
            cpfTitular = state.cpf,
            tipoCartao = state.selectedType
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            _apiError.value = null

            try {
                val response = RetrofitClient.api.savePaymentMethod(userId, request)

                if (response.isSuccessful) {

                    onSuccess()
                } else {

                    handleApiError(response.code(), response.errorBody()?.string())
                }
            } catch (e: Exception) {

                _apiError.value = "Falha na conexão ou erro inesperado. Tente novamente."
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun handleApiError(statusCode: Int, errorBody: String?) {
        val errorObj = try {
            errorBody?.let { Gson().fromJson(it, ErrorResponse::class.java) }
        } catch (e: Exception) { null }

        when (statusCode) {
            400 -> {
                _apiError.value = errorObj?.message ?: "Erro de validação nos dados do cartão (Status 400)."
            }
            409 -> {
                _apiError.value = errorObj?.message ?: "Este cartão já está cadastrado em sua carteira (Status 409)."
            }
            else -> {

                _apiError.value = errorObj?.message ?: "Ocorreu um erro no servidor. Tente mais tarde."
            }
        }
    }
}