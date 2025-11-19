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

data class PartnerDetails(
    val cpf: String = "",
    val rg: String = "",
    val orgaoEmissor: String = "",
    val nomeCompleto: String = "",
    val email: String = "",
    val telefone: String = "",
    val status: PartnerStatus = PartnerStatus.INICIAL
)


