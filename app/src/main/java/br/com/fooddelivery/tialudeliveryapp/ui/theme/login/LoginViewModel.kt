package br.com.fooddelivery.tialudeliveryapp.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    var nome by mutableStateOf("")
        private set
    var sobrenome by mutableStateOf("")
        private set
    var telefone by mutableStateOf("")
        private set
    var cep by mutableStateOf("")
        private set
    var endereco by mutableStateOf("")
        private set
    var numeroCasa by mutableStateOf("")
        private set
    var complemento by mutableStateOf("")
        private set
    var bairro by mutableStateOf("")
        private set

    fun onEmailChange(newVal: String) { email = newVal }
    fun onPasswordChange(newVal: String) { password = newVal }
    fun onNomeChange(newVal: String) { nome = newVal }
    fun onSobrenomeChange(newVal: String) { sobrenome = newVal }
    fun onTelefoneChange(newVal: String) { telefone = newVal }
    fun onCepChange(newVal: String) { cep = newVal }
    fun onEnderecoChange(newVal: String) { endereco = newVal }
    fun onNumeroCasaChange(newVal: String) { numeroCasa = newVal }
    fun onComplementoChange(newVal: String) { complemento = newVal }
    fun onBairroChange(newVal: String) { bairro = newVal }

    fun toggleLoginMode() {
        _uiState.update { it.copy(isRegisterMode = !it.isRegisterMode, error = null) }
    }

    fun doAction() {
        if (email.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(error = "Preencha email e senha") }
            return
        }

        if (_uiState.value.isRegisterMode && (nome.isBlank() || telefone.isBlank())) {
            _uiState.update { it.copy(error = "Preencha os dados pessoais") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            delay(2000)
            _uiState.update { it.copy(isLoading = false, isSuccess = true) }
        }
    }
}