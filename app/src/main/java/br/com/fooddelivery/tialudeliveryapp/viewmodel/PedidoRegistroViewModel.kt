package br.com.fooddelivery.tialudeliveryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import br.com.fooddelivery.tialudeliveryapp.models.*
import br.com.fooddelivery.tialudeliveryapp.repository.PedidoDataSource

class PedidoRegistroViewModel(
    private val dataSource: PedidoDataSource,
    private val usuarioId: String
) : ViewModel() {

    private val _estado = MutableStateFlow<ResultadoPedido>(ResultadoPedido.Idle)
    val estado: StateFlow<ResultadoPedido> = _estado

    fun registrarPedido(request: PedidoReq) {
        viewModelScope.launch {

            _estado.value = ResultadoPedido.Carregando

            // CA 1.2 - Validação de obrigatórios
            if (request.idEstabelecimento.isBlank() ||
                request.idEnderecoEntrega.isBlank() ||
                request.itens.isEmpty() ||
                request.valorTotalEnviado <= 0.0
            ) {
                return@launch erro(
                    ErroRes(
                        codigoErro = "MISSING_FIELDS",
                        mensagem = "Campos obrigatórios não preenchidos."
                    )
                )
            }

            // CA 1.3 - Endereço existe e pertence ao usuário
            val endereco = when (val res = dataSource.obterEndereco(request.idEnderecoEntrega)) {
                is Resultado.Sucesso -> res.dado
                is Resultado.Erro -> return@launch erro(
                    ErroRes(
                        codigoErro = "ADDRESS_NOT_FOUND",
                        mensagem = res.mensagem,
                        acaoSugerida = "Verifique se o endereço ainda está salvo em sua carteira ou utilize um ID válido."
                    )
                )
            }

            if (endereco.usuarioId != usuarioId) {
                return@launch erro(
                    ErroRes(
                        codigoErro = "ADDRESS_NOT_FOUND",
                        mensagem = "O ID de endereço de entrega '${request.idEnderecoEntrega}' não pertence à sua conta.",
                        acaoSugerida = "Verifique se o endereço ainda está salvo em sua carteira ou utilize um ID válido."
                    )
                )
            }

            // CA 1.4 - Estabelecimento cobre o endereço
            val estabelecimento = when (val res = dataSource.obterEstabelecimento(request.idEstabelecimento)) {
                is Resultado.Sucesso -> res.dado
                is Resultado.Erro -> return@launch erro(
                    ErroRes(
                        codigoErro = "ESTABLISHMENT_NOT_FOUND",
                        mensagem = res.mensagem
                    )
                )
            }

            if (!estabelecimento.bairrosAtendidos.contains(endereco.bairro)) {
                return@launch erro(
                    ErroRes(
                        codigoErro = "DELIVERY_OUT_OF_AREA",
                        mensagem = "O estabelecimento não realiza entregas para o endereço vinculado ao ID '${request.idEnderecoEntrega}'.",
                        detalhes = mapOf(
                            "estabelecimento" to estabelecimento.nome,
                            "cepDoEndereco" to endereco.cep
                        )
                    )
                )
            }

            // CA 1.5 - Produtos pertencem ao estabelecimento
            val ids = request.itens.map { it.idProduto }

            val produtos = when (val res = dataSource.obterProdutos(ids)) {
                is Resultado.Sucesso -> res.dado
                is Resultado.Erro -> return@launch erro(
                    ErroRes(
                        codigoErro = "PRODUCTS_NOT_FOUND",
                        mensagem = res.mensagem
                    )
                )
            }

            if (produtos.size != ids.size) {
                return@launch erro(
                    ErroRes(
                        codigoErro = "PRODUCTS_NOT_FOUND",
                        mensagem = "Há itens que não existem ou estão indisponíveis."
                    )
                )
            }

            produtos.forEach { produto ->
                if (produto.idEstabelecimento != request.idEstabelecimento) {
                    return@launch erro(
                        ErroRes(
                            codigoErro = "PRODUCT_OUT_OF_ESTABLISHMENT",
                            mensagem = "Um dos produtos não pertence ao estabelecimento informado."
                        )
                    )
                }
            }

            // CA 1.7 - Validação de estoque
            request.itens.forEach { item ->
                val produto = produtos.find { it.id == item.idProduto }!!
                if (item.quantidade > produto.estoque) {
                    return@launch erro(
                        ErroRes(
                            codigoErro = "OUT_OF_STOCK",
                            mensagem = "Produto '${produto.id}' sem estoque suficiente."
                        )
                    )
                }
            }

            // CA 1.6 - Recalcular total, validar fraude
            val subtotal = produtos.sumOf { produto ->
                val qtd = request.itens.find { it.idProduto == produto.id }!!.quantidade
                produto.preco * qtd
            }

            val descontoAplicado = request.desconto?.let {
                when (it.tipoDesconto.uppercase()) {
                    "FIXO" -> it.valorDesconto
                    "PERCENTUAL" -> subtotal * (it.valorDesconto / 100)
                    else -> 0.0
                }
            } ?: 0.0

            val totalCalculado = subtotal - descontoAplicado + estabelecimento.taxaEntrega

            if (totalCalculado != request.valorTotalEnviado) {
                return@launch erro(
                    ErroRes(
                        codigoErro = "TOTAL_MISMATCH",
                        mensagem = "Valor divergente. Enviado: ${request.valorTotalEnviado}, Calculado: $totalCalculado"
                    )
                )
            }

            // CA 1.1 - Criar pedido (chamada final)
            when (val res = dataSource.enviarPedido(request)) {
                is Resultado.Sucesso ->
                    _estado.value = ResultadoPedido.Sucesso(res.dado.idPedido)

                is Resultado.Erro ->
                    erro(
                        ErroRes(
                            codigoErro = "ORDER_CREATION_FAILED",
                            mensagem = res.mensagem
                        )
                    )
            }
        }
    }

    private fun erro(erroRes: ErroRes) {
        _estado.value = ResultadoPedido.Erro(erroRes)
    }
}
