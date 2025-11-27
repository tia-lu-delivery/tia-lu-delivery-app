package br.com.fooddelivery.tialudeliveryapp.dto

data class EstabelecimentoDTO(
    val id: String,
    val nome: String,
    val taxaEntrega: Double,
    val bairrosAtendidos: List<String>
)
