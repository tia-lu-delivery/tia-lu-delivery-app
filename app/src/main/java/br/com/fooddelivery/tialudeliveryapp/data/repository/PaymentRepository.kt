package br.com.fooddelivery.tialudeliveryapp.data.repository

import br.com.fooddelivery.tialudeliveryapp.data.model.PaymentMethod

interface PaymentRepository {
    suspend fun getPaymentMethods(): List<PaymentMethod>
}
