package br.com.fooddelivery.tialudeliveryapp.data.network

import com.google.gson.annotations.SerializedName

data class OrderDto(
    val id: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("openedAt") val openedAt: String,
    val customerName: String,
    val items: List<String>,
    val totalPrice: Double,
    val status: String
)
