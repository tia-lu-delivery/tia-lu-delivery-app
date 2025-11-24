package br.com.fooddelivery.tialudeliveryapp.models

sealed class ResultadoPedido {
    data class Sucesso(val pedidoId: String) : ResultadoPedido()
    data class Erro(val mensagem: String) : ResultadoPedido()
    object Carregando : ResultadoPedido()
    object Idle : ResultadoPedido()
}