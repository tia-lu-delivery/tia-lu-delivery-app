package br.com.fooddelivery.tialudeliveryapp.viewmodel

import android.content.Context
import android.widget.Toast
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

    fun registrarPagamento(context: Context, editId: Int? = null) {

        val valorDouble = valor.value.replace(",", ".").toDoubleOrNull()
        if (valorDouble == null || valorDouble <= 0.0) {
            mensagem.value = "Informe um valor válido"

            val toast = Toast.makeText(context, "Erro: Valor inválido!", Toast.LENGTH_LONG)
            toast.show()

            Thread {
                Thread.sleep(30000)
                limparMensagem()
            }.start()

            return
        }

        if (editId != null) {
            listaPagamentos.value = listaPagamentos.value.map { p ->
                if (p.id == editId) p.copy(valor = valorDouble, forma = formaPagamento.value)
                else p
            }
            mensagem.value = "Pagamento editado com sucesso!"

            val toast = Toast.makeText(context, "Pagamento atualizado!", Toast.LENGTH_LONG)
            toast.show()

        } else {
            val novo = Pagamento(contadorId++, valorDouble, formaPagamento.value)
            listaPagamentos.value = listaPagamentos.value + novo
            mensagem.value = "Pagamento registrado com sucesso!"

            val toast = Toast.makeText(context, "Pagamento adicionado!", Toast.LENGTH_LONG)
            toast.show()
        }

        Thread {
            Thread.sleep(30000)
            limparMensagem()
        }.start()

        valor.value = ""
        formaPagamento.value = "Dinheiro"
    }

    fun excluirPagamento(context: Context, id: Int) {
        listaPagamentos.value = listaPagamentos.value.filter { it.id != id }
        mensagem.value = "Pagamento excluído"

        val toast = Toast.makeText(context, "Pagamento removido!", Toast.LENGTH_LONG)
        toast.show()

        Thread {
            Thread.sleep(30000)
            limparMensagem()
        }.start()
    }

    fun editarPagamento(pagamento: Pagamento) {
        valor.value = pagamento.valor.toString()
        formaPagamento.value = pagamento.forma
    }
}
