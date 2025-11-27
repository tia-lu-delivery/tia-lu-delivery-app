package br.com.fooddelivery.tialudeliveryapp.DTO

data class DescontoDTO(
    val codigoCupom: String,
    val valorDesconto: Double,
    val tipoDesconto: String
)