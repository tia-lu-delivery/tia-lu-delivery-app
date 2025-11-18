package br.com.fooddelivery.tialudeliveryapp.ui.screens.payment

data class PaymentUiState(
    val selectedType: String = "CREDITO",
    val cardNumber: String = "",
    val cardError: Boolean = false,
    val expiryDate: String = "",
    val dateError: Boolean = false,
    val cvv: String = "",
    val cvvError: Boolean = false,
    val cardName: String = "",
    val cpf: String = "",
    val cpfError: Boolean = false,
    val isLoading: Boolean = false
) {
    val isValid: Boolean
        get() = cardNumber.isNotBlank() && !cardError &&
                expiryDate.length == 4 && !dateError &&
                cvv.length >= 3 && !cvvError &&
                cardName.isNotBlank() &&
                cpf.length == 11 && !cpfError
}