package br.com.fooddelivery.tialudeliveryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

enum class PartnerStatus {
    INICIAL,
    DADOS_PESSOAIS,
    DOCUMENTOS,
    FINALIZADO
}

data class Address(
    val cep: String = "",
    val logradouro: String = "",
    val complemento: String = "",
    val cidade: String = "",
    val estado: String = ""
)

data class Establishment(
    val cnpj: String ="",
    val razaoSocial: String = "",
    val nomeFantasia: String = "",
    val inscricaoEstadual: String = "",
    val endereco: Address = Address()
)

data class PartnerDetails(
    val cpf: String = "",
    val rg: String = "",
    val orgaoEmissor: String = "",
    val nomeCompleto: String = "",
    val email: String = "",
    val telefone: String = "",
    val status: PartnerStatus = PartnerStatus.INICIAL
)


