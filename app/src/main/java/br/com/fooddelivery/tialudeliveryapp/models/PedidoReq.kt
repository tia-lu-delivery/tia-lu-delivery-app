package br.com.fooddelivery.tialudeliveryapp.models

data class Desconto(
    val codigoCupom: String,
    val valorDesconto: Double,
    val tipoDesconto: String // FIXO ou PERCENTUAL
)

data class PedidoReq(
    val idEstabelecimento: String,
    val valorTotalEnviado: Double,
    val observacoesGerais: String?,
    val idEnderecoEntrega: String,
    val itens: List<PedidoItemReq>,
    val desconto: Desconto? = null
)

