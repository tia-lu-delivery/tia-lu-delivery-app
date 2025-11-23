package br.com.fooddelivery.tialudeliveryapp.ENDERECOS.DTO

package com.exemplo.enderecos.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class EnderecosResponse(
    @JsonProperty("total_enderecos") val totalEnderecos: Int,
    val enderecos: List<EnderecoDto>
)
