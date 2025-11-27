package br.com.fooddelivery.tialudeliveryapp.dto

data class ErroRes(
    val codigoErro: String,
    val mensagem: String,
    val acaoSugerida: String? = null,
    val detalhes: Map<String, String>? = null
)
