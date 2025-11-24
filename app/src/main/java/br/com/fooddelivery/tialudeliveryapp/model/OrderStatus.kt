package br.com.fooddelivery.tialudeliveryapp.model

enum class OrderStatus {
    ACEITO,
    FAZENDO,
    FEITO,
    SAIU_PARA_ENTREGA,
    ENTREGUE,
    CANCELADO,
    REJEITADO
}

fun OrderStatus.toLabelPt(): String = when (this) {
    OrderStatus.ACEITO -> "Aceito"
    OrderStatus.FAZENDO -> "Em preparo"
    OrderStatus.FEITO -> "Feito"
    OrderStatus.SAIU_PARA_ENTREGA -> "Saiu para entrega"
    OrderStatus.ENTREGUE -> "Entregue"
    OrderStatus.CANCELADO -> "Cancelado"
    OrderStatus.REJEITADO -> "Rejeitado"
}