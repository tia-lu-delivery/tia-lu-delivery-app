package br.com.fooddelivery.tialudeliveryapp.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateMenuRequest (
    val name: String
)

@Serializable
data class CreateMenuResponse (
    val id: String
)