package br.com.fooddelivery.tialudeliveryapp.model

enum class OrderStatus {
    AWAITING_APPROVAL,
    ACCEPTED,
    COOKING,
    DONE,
    WAITING_DELIVERY,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELED,
    REJECTED
}

fun OrderStatus.toLabelPt(): String = when (this) {
    OrderStatus.AWAITING_APPROVAL -> "Aguardando aprovação"
    OrderStatus.ACCEPTED -> "Aceito"
    OrderStatus.COOKING -> "Em preparo"
    OrderStatus.DONE -> "Feito"
    OrderStatus.WAITING_DELIVERY -> "Esperando entregador"
    OrderStatus.OUT_FOR_DELIVERY -> "Saiu para entrega"
    OrderStatus.DELIVERED -> "Entregue"
    OrderStatus.CANCELED -> "Cancelado"
    OrderStatus.REJECTED -> "Rejeitado"
}
