package br.com.fooddelivery.tialudeliveryapp.data.repository

import br.com.fooddelivery.tialudeliveryapp.data.model.PaymentMethod
import kotlinx.coroutines.delay

class FakePaymentRepository : PaymentRepository {
    override suspend fun getPaymentMethods(): List<PaymentMethod> {
        // Simula delay de rede (1.5s)
        delay(1500)

        return listOf(
            PaymentMethod(
                id = "MP001",
                flagUrl = "https://img.carteira.com/visa.svg",
                last4Digits = "4321",
                cardType = "CREDITO",
                holderName = "Estabelecimento Exemplo LTDA",
                expiryDate = "12/28"
            ),
            PaymentMethod(
                id = "MP002",
                flagUrl = "https://img.carteira.com/mastercard.svg",
                last4Digits = "8765",
                cardType = "DEBITO",
                holderName = "Estabelecimento Exemplo LTDA",
                expiryDate = "05/26"
            ),
            PaymentMethod(
                id = "MP003",
                flagUrl = "https://img.carteira.com/alelo.svg",
                last4Digits = "1111",
                cardType = "REFEICAO",
                holderName = "Estabelecimento Exemplo LTDA",
                expiryDate = "09/27"
            )
        )
    }
}
