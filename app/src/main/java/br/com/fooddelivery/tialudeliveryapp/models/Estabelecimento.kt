package br.com.fooddelivery.tialudeliveryapp.models

data class Estabelecimento(
    val id: String,
    val nome: String,
    val taxaEntrega: Double,
    val bairrosAtendidos: List<String>
)