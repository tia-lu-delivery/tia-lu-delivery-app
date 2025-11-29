package br.com.fooddelivery.tialudeliveryapp.models

sealed class ResultadoPedido {
    data class Sucesso(val pedidoId: String) : ResultadoPedido()
    data class Erro(val erro: ErroRes) : ResultadoPedido()
    object Carregando : ResultadoPedido()
    object Idle : ResultadoPedido()
}
