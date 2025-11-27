package br.com.fooddelivery.tialudeliveryapp.models

data class Endereco(
    val id: String,
    val cep: String,
    val tipoLogradouro: String,
    val logradouro: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val complemento: String?,
    val tipo: String,
    val padraoEntrega: Boolean,
    val usuarioId: String? = null
)