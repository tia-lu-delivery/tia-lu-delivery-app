package br.com.fooddelivery.tialudeliveryapp.data

import com.google.gson.annotations.SerializedName


data class PaymentRequest(
    @SerializedName("numeroCartao") val numeroCartao: String,
    @SerializedName("validadeMes") val validadeMes: Int,
    @SerializedName("validadeAno") val validadeAno: Int,
    @SerializedName("cvv") val cvv: String,
    @SerializedName("nomeTitular") val nomeTitular: String,
    @SerializedName("cpfTitular") val cpfTitular: String,
    @SerializedName("tipoCartao") val tipoCartao: String
)

data class PaymentResponse(
    @SerializedName("idMeioPagamento") val id: String,
    @SerializedName("mensagem") val mensagem: String
)

data class ErrorResponse(
    @SerializedName("codigoErro") val code: String?,
    @SerializedName("mensagem") val message: String?
)