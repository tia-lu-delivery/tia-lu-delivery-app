package br.com.fooddelivery.tialudeliveryapp.models

data class Desconto(
    val codigoCupom: String,
    val valorDesconto: Double,
    val tipoDesconto: String
)

data class PedidoReq(
    val idEstabelecimento: String,
    val idEnderecoEntrega: String,
    val itens: List<PedidoItemReq>,
    val valorTotalEnviado: Double,
    val observacoesGerais: String? = null,
    val desconto: Desconto? = null
)

