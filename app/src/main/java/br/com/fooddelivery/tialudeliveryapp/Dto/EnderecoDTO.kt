package br.com.fooddelivery.tialudeliveryapp.dto

data class EnderecoDTO(
    val id: String,
    val usuarioId: String,
    val cep: String,
    val cidade: String,
    val bairro: String,
    val rua: String,
    val numero: String
)

