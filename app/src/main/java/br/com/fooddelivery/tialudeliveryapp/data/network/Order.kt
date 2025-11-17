package br.com.fooddelivery.tialudeliveryapp.data.network

import com.google.gson.annotations.SerializedName

data class OrderDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("userName")
    val userName: String,

    @SerializedName("openedAt")
    val openedAt: String,

    @SerializedName("customerName")
    val customerName: String,

    @SerializedName("customerPhone")
    val customerPhone: String,

    @SerializedName("deliveryAddress")
    val deliveryAddress: String,

    @SerializedName("items")
    val items: List<OrderItemDto>,

    @SerializedName("totalPrice")
    val totalPrice: Double,

    @SerializedName("status")
    val status: String
)

class OrderItemDto(val id: String, val name: String, val quantity: Int, val price: Double) {

}
