package br.com.fooddelivery.tialudeliveryapp.ENDERECOS.DTO

package com.exemplo.enderecos.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class EnderecoDto(
    @JsonProperty("id_endereco") val idEndereco: Long,
    val cep: String,
    @JsonProperty("tipo_logradouro") val tipoLogradouro: String,
    val logradouro: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val complemento: String?,
    val tipo: String,
    @JsonProperty("padrao_entrega") val padraoEntrega: Boolean
)
