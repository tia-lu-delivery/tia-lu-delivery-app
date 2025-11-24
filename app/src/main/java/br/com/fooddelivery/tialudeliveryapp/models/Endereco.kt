package br.com.fooddelivery.tialudeliveryapp.models

data class Endereco(
    val id: String,
    val usuarioId: String,
    val cep: String,
    val cidade: String,
    val bairro: String,
    val rua: String,
    val numero: String
)
