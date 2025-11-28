package br.com.fooddelivery.tialudeliveryapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Pagamento(
    val id: Int,
    val valor: Double,
    val forma: String
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

    /**
     * Atualiza o valor digitado (campo de texto)
     */
    fun setValor(novoValor: String) {
        valor.value = novoValor
    }

    /**
     * Atualiza a forma de pagamento selecionada
     */
    fun setFormaPagamento(forma: String) {
        formaPagamento.value = forma
    }

    /**
     * Função auxiliar para definir mensagens e limpar automaticamente
     */
    private fun setMensagem(texto: String) {
        mensagem.value = texto

        // 🔄 LIMPAR AUTOMÁTICO (opcional)
        viewModelScope.launch {
            delay(2500)
            mensagem.value = ""
        }
    }

    /**
     * Registra um novo pagamento ou edita um existente
     */
    fun registrarPagamento(editId: Int? = null) {

        // Validação robusta
        val valorDouble = valor.value
            .trim()
            .replace(",", ".")
            .toDoubleOrNull()

        if (valor.value.isBlank()) {
            setMensagem("O campo valor não pode estar vazio")
            return
        }

        if (valorDouble == null || valorDouble <= 0.0) {
            setMensagem("Informe um valor válido")
            return
        }

        if (editId != null) {
            // Editando pagamento existente
            listaPagamentos.value = listaPagamentos.value.map { pag ->
                if (pag.id == editId)
                    pag.copy(valor = valorDouble, forma = formaPagamento.value)
                else pag
            }

            setMensagem("Pagamento editado com sucesso!")
        } else {
            // Criando novo pagamento
            val novo = Pagamento(
                id = contadorId++,
                valor = valorDouble,
                forma = formaPagamento.value
            )

            listaPagamentos.value = listaPagamentos.value + novo
            setMensagem("Pagamento registrado com sucesso!")
        }

        // Resetar campos
        valor.value = ""
        formaPagamento.value = "Dinheiro"
    }

    /**
     * Excluir pagamento pelo ID
     */
    fun excluirPagamento(id: Int) {
        listaPagamentos.value = listaPagamentos.value.filter { it.id != id }
        setMensagem("Pagamento excluído")
    }

    /**
     * Preencher os campos quando o usuário clicar em editar
     */
    fun editarPagamento(pagamento: Pagamento) {
        valor.value = pagamento.valor.toString().replace(".", ",")
        formaPagamento.value = pagamento.forma
    }
}
