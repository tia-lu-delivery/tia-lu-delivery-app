package br.com.fooddelivery.tialudeliveryapp.ui.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val isRegisterMode: Boolean = false
)