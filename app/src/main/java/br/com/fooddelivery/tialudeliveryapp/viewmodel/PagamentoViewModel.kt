package br.com.fooddelivery.tialudeliveryapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class Pagamento(
    val id: Int,
    var valor: Double,
    var forma: String
)

class PagamentoViewModel : ViewModel() {

    var valor = mutableStateOf("")
        private set

    var formaPagamento = mutableStateOf("Dinheiro")
        private set

    var mensagem = mutableStateOf("")
        private set

    var listaPagamentos = mutableStateOf<List<Pagamento>>(emptyList())
        private set

    private var contadorId = 1

    fun setValor(novoValor: String) {
        valor.value = novoValor
    }

    fun setFormaPagamento(forma: String) {
        formaPagamento.value = forma
    }

    fun limparMensagem() {
        mensagem.value = ""
    }

    fun registrarPagamento(editId: Int? = null) {
        val valorDouble = valor.value.replace(",", ".").toDoubleOrNull()
        if (valorDouble == null || valorDouble <= 0.0) {
            mensagem.value = "Informe um valor válido"
            return
        }

        if (editId != null) {
            listaPagamentos.value = listaPagamentos.value.map { p ->
                if (p.id == editId) p.copy(valor = valorDouble, forma = formaPagamento.value)
                else p
            }
            mensagem.value = "Pagamento editado com sucesso!"
        } else {
            val novo = Pagamento(contadorId++, valorDouble, formaPagamento.value)
            listaPagamentos.value = listaPagamentos.value + novo
            mensagem.value = "Pagamento registrado com sucesso!"
        }

        valor.value = ""
        formaPagamento.value = "Dinheiro"
    }

    fun excluirPagamento(id: Int) {
        listaPagamentos.value = listaPagamentos.value.filter { it.id != id }
        mensagem.value = "Pagamento excluído"
    }

    fun editarPagamento(pagamento: Pagamento) {
        valor.value = pagamento.valor.toString()
        formaPagamento.value = pagamento.forma
    }
}
