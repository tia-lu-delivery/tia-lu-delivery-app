package br.com.fooddelivery.tialudeliveryapp.DTO

data class PedidoErroResDTO(
    val codigoErro: String,
    val mensagem: String,
    val acaoSugerida: String? = null,
    val detalhes: Map<String, String>? = null
)
