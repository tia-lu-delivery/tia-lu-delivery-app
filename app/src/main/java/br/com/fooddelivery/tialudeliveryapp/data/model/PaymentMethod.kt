package br.com.fooddelivery.tialudeliveryapp.data.model

data class PaymentMethod(
    val id: String,
    val flagUrl: String,
    val last4Digits: String,
    val cardType: String,
    val holderName: String,
    val expiryDate: String
)