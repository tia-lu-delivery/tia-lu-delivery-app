package br.com.fooddelivery.tialudeliveryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData



class PartnerDetailsViewModel : ViewModel() {

    private val _partnerDetails = MutableLiveData<PartnerDetails>()
    val partnerDetails: LiveData<PartnerDetails> = _partnerDetails

    init {
        _partnerDetails.value = PartnerDetails()
    }

    fun moveForwardStatus() {
        val current = _partnerDetails.value ?: return
        val nextStatus = when (current.status) {
            PartnerStatus.INICIAL -> PartnerStatus.DADOS_PESSOAIS
            PartnerStatus.DADOS_PESSOAIS -> PartnerStatus.DOCUMENTOS
            PartnerStatus.DOCUMENTOS -> PartnerStatus.FINALIZADO
            PartnerStatus.FINALIZADO -> PartnerStatus.FINALIZADO
        }
        _partnerDetails.value = current.copy(status = nextStatus)
    }

    fun getTextButton(): String {
        return when (_partnerDetails.value?.status) {
            PartnerStatus.INICIAL -> "Iniciar Cadastro"
            PartnerStatus.DADOS_PESSOAIS -> "Preencher Documentos"
            PartnerStatus.DOCUMENTOS -> "Finalizar Cadastro"
            PartnerStatus.FINALIZADO -> "Cadastro Concluído"
            null -> "..."
        }
    }

    fun updateCpf(cpf: String) {
        _partnerDetails.value = _partnerDetails.value?.copy(cpf = cpf)
    }

    fun updateRg(rg: String) {
        _partnerDetails.value = _partnerDetails.value?.copy(rg = rg)
    }

    fun updateOrgaoEmissor(orgaoEmissor: String) {
        _partnerDetails.value = _partnerDetails.value?.copy(orgaoEmissor = orgaoEmissor)
    }

    fun updateNomeCompleto(nomeCompleto: String) {
        _partnerDetails.value = _partnerDetails.value?.copy(nomeCompleto = nomeCompleto)
    }

    fun updateEmail(email: String) {
        _partnerDetails.value = _partnerDetails.value?.copy(email = email)
    }

    fun updateTelefone(telefone: String) {
        _partnerDetails.value = _partnerDetails.value?.copy(telefone = telefone)
    }

    fun clearFields() {
        _partnerDetails.value = PartnerDetails()
    }
}