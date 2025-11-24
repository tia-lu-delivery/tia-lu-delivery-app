package br.com.fooddelivery.tialudeliveryapp.models

data class ErroRes(
    val codigoErro: String,
    val mensagem: String,
    val acaoSugerida: String? = null,
    val detalhes: Map<String, String>? = null
)

sealed class ResultadoPedido {
    data class Sucesso(val pedidoId: String) : ResultadoPedido()
    data class Erro(val erro: ErroRes) : ResultadoPedido()
    object Carregando : ResultadoPedido()
    object Idle : ResultadoPedido()
}
