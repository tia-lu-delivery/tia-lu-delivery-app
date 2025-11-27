package br.com.fooddelivery.tialudeliveryapp.models

data class ErroRes(
    val codigoErro: String,
    val mensagem: String,
    val acaoSugerida: String? = null,
    val detalhes: Map<String, String>? = null,
    val httpStatus: Int? = null //mapear para UI
)
