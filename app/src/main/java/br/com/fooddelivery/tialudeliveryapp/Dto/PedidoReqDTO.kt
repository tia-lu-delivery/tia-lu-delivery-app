package br.com.fooddelivery.tialudeliveryapp.dto

import br.com.fooddelivery.tialudeliveryapp.repository.dto.PedidoItemReqDTO

data class Desconto(
    val codigoCupom: String,
    val valorDesconto: Double,
    val tipoDesconto: String
)

data class PedidoReqDTO(
    val idEstabelecimento: String,
    val idEnderecoEntrega: String,
    val itens: List<PedidoItemReqDTO>,
    val valorTotalEnviado: Double,
    val observacoesGerais: String? = null,
    val desconto: Desconto? = null
)