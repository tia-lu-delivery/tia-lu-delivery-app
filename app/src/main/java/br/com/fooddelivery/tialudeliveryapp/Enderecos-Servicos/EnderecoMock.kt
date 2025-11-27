package com.exemplo.enderecos.model

data class EnderecoMock(
    val id: Long,
    val cep: String,
    val tipoLogradouro: String,
    val logradouro: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val complemento: String?,
    val tipo: String,
    val padraoEntrega: Boolean
)
