package br.com.fooddelivery.tialudeliveryapp.DTO

data class PedidoItemDTO(
    val idProduto: String,
    val quantidade: Int,
    val precoUnitarioMomentoCompra: Double
)
