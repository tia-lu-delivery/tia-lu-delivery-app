package br.com.fooddelivery.tialudeliveryapp.data.network

import com.google.gson.annotations.SerializedName

data class OrderDto(
    val id: String,

    // Alguns backends usam "userName", outros "customerName". Mantemos ambos.
    @SerializedName("userName")
    val userName: String? = null,

    @SerializedName("customerName")
    val customerName: String? = null,

    @SerializedName("openedAt")
    val openedAt: String,

    @SerializedName("customerPhone")
    val customerPhone: String? = null,

    @SerializedName("deliveryAddress")
    val deliveryAddress: String? = null,

    @SerializedName("items")
    val items: List<OrderItemDto> = emptyList(),

    @SerializedName("totalPrice")
    val totalPrice: Double? = null,

    @SerializedName("status")
    val status: String? = null
)