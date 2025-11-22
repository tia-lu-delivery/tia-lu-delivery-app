package com.exemplo.enderecos.model

data class ErroResponse(val erro: DetalheErro)

data class DetalheErro(
    val codigo: String,
    val detalhe: String
)
