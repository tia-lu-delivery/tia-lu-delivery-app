package com.exemplo.enderecos.model

import com.fasterxml.jackson.annotation.JsonProperty

data class EnderecosResponse(
    @JsonProperty("total_enderecos") val totalEnderecos: Int,
    val enderecos: List<EnderecoDto>
)
