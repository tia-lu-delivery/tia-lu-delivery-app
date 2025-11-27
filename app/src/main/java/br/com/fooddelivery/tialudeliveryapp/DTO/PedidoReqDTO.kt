package br.com.fooddelivery.tialudeliveryapp.DTO

data class PedidoReqDTO(
    val idEstabelecimento: String,
    val valorTotalEnviado: Double,
    val observacoesGerais: String?,
    val idEnderecoEntrega: String,
    val itens: List<PedidoItemDTO>,
    val desconto: DescontoDTO? = null
)