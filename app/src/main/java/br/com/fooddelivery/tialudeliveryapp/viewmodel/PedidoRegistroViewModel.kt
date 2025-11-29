package br.com.fooddelivery.tialudeliveryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fooddelivery.tialudeliveryapp.models.*
import br.com.fooddelivery.tialudeliveryapp.repository.PedidoDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs

class PedidoRegistroViewModel(
    private val dataSource: PedidoDataSource,
    private val usuarioId: String
) : ViewModel() {

    private val _estado = MutableStateFlow<ResultadoPedido>(ResultadoPedido.Idle)
    val estado: StateFlow<ResultadoPedido> = _estado

    private val EPSILON = 0.01

    fun registrarPedido(request: PedidoReq) {
        viewModelScope.launch {
            _estado.value = ResultadoPedido.Carregando

            // CA 1.2 - obrigatórios
            validarObrigatorios(request)?.let {
                return@launch erro(it)
            }

            // CA 1.3 - validar endereço
            val endereco = when (val r = dataSource.obterEndereco(request.idEnderecoEntrega)) {
                is Resultado.Sucesso -> r.dado
                is Resultado.Erro -> return@launch erro(
                    ErroRes(
                        codigoErro = "ADDRESS_NOT_FOUND",
                        mensagem = "Endereço não encontrado ou não pertence ao usuário.",
                        acaoSugerida = "Verifique seu endereço",
                        httpStatus = 404
                    )
                )
            }

            if (endereco.usuarioId != usuarioId) {
                return@launch erro(
                    ErroRes(
                        codigoErro = "ADDRESS_NOT_OWNED",
                        mensagem = "O endereço informado não pertence ao usuário autenticado.",
                        acaoSugerida = "Selecione um endereço cadastrado.",
                        httpStatus = 403
                    )
                )
            }

            // CA 1.4 - cobertura de entrega
            val estabelecimento = when (val r = dataSource.obterEstabelecimento(request.idEstabelecimento)) {
                is Resultado.Sucesso -> r.dado
                is Resultado.Erro -> return@launch erro(
                    ErroRes(
                        codigoErro = "ESTABLISHMENT_NOT_FOUND",
                        mensagem = "Estabelecimento não encontrado",
                        httpStatus = 404
                    )
                )
            }

            if (!estabelecimento.bairrosAtendidos.contains(endereco.bairro)) {
                return@launch erro(
                    ErroRes(
                        codigoErro = "DELIVERY_OUT_OF_AREA",
                        mensagem = "O estabelecimento não entrega no bairro informado",
                        detalhes = mapOf("bairro" to endereco.bairro),
                        httpStatus = 422
                    )
                )
            }

            // CA 1.5 / 1.7 - produtos do estabelecimento + estoque
            val idsProdutos = request.itens.map { it.idProduto }
            val produtos = when (val r = dataSource.obterProdutos(request.idEstabelecimento, idsProdutos)) {
                is Resultado.Sucesso -> r.dado
                is Resultado.Erro -> return@launch erro(
                    ErroRes(
                        codigoErro = "PRODUCTS_NOT_FOUND",
                        mensagem = "Produtos não encontrados",
                        httpStatus = 404
                    )
                )
            }

            val produtosFora = produtos.filter { it.idEstabelecimento != request.idEstabelecimento }
            if (produtosFora.isNotEmpty()) {
                return@launch erro(
                    ErroRes(
                        codigoErro = "PRODUCT_OUT_OF_ESTABLISHMENT",
                        mensagem = "Produtos não pertencem ao estabelecimento",
                        detalhes = mapOf("produtos" to produtosFora.joinToString { it.id }),
                        httpStatus = 409
                    )
                )
            }

            request.itens.forEach { item ->
                val produto = produtos.find { it.id == item.idProduto }
                if (produto == null) return@launch erro(
                    ErroRes(
                        codigoErro = "PRODUCT_NOT_FOUND",
                        mensagem = "Produto ${item.idProduto} não encontrado",
                        httpStatus = 404
                    )
                )

                if (item.quantidade <= 0) return@launch erro(
                    ErroRes(
                        codigoErro = "INVALID_QUANTITY",
                        mensagem = "Quantidade inválida para ${item.idProduto}",
                        httpStatus = 400
                    )
                )

                if (item.quantidade > produto.estoque) return@launch erro(
                    ErroRes(
                        codigoErro = "OUT_OF_STOCK",
                        mensagem = "Produto sem estoque suficiente",
                        detalhes = mapOf(
                            "estoque" to produto.estoque.toString(),
                            "pedido" to item.quantidade.toString()
                        ),
                        httpStatus = 409
                    )
                )
            }

            // CA 1.6 - validação de preço
            val subtotal = produtos.sumOf { prod ->
                val qtd = request.itens.find { it.idProduto == prod.id }?.quantidade ?: 0
                prod.preco * qtd
            }

            val descontoAplicado = request.desconto?.let {
                when (it.tipoDesconto.uppercase()) {
                    "FIXO" -> it.valorDesconto
                    "PERCENTUAL" -> subtotal * (it.valorDesconto / 100.0)
                    else -> 0.0
                }
            } ?: 0.0

            val totalCalculado = subtotal - descontoAplicado + estabelecimento.taxaEntrega

            if (abs(totalCalculado - request.valorTotalEnviado) > EPSILON) {
                return@launch erro(
                    ErroRes(
                        codigoErro = "TOTAL_MISMATCH",
                        mensagem = "Valor divergente. Enviado: ${request.valorTotalEnviado}, Calculado: $totalCalculado",
                        detalhes = mapOf(
                            "enviado" to request.valorTotalEnviado.toString(),
                            "calculado" to totalCalculado.toString()
                        ),
                        httpStatus = 422
                    )
                )
            }

            // CA 1.1 - enviar pedido
            when (val r = dataSource.enviarPedido(request)) {
                is Resultado.Sucesso -> _estado.value = ResultadoPedido.Sucesso(r.dado.idPedido)
                is Resultado.Erro -> erro(
                    ErroRes(
                        codigoErro = "ORDER_CREATION_FAILED",
                        mensagem = r.mensagem,
                        httpStatus = 500
                    )
                )
            }
        }
    }

    private fun validarObrigatorios(req: PedidoReq): ErroRes? {
        if (req.idEstabelecimento.isBlank()) return ErroRes("MISSING_FIELD", "idEstabelecimento é obrigatório", httpStatus = 400)
        if (req.idEnderecoEntrega.isBlank()) return ErroRes("MISSING_FIELD", "idEnderecoEntrega é obrigatório", httpStatus = 400)
        if (req.itens.isEmpty()) return ErroRes("MISSING_FIELD", "A lista de itens não pode ser vazia", httpStatus = 400)
        if (req.valorTotalEnviado <= 0) return ErroRes("INVALID_TOTAL", "valorTotalEnviado deve ser maior que zero", httpStatus = 400)
        return null
    }

    private fun erro(erroRes: ErroRes) {
        _estado.value = ResultadoPedido.Erro(erroRes)
    }
}
