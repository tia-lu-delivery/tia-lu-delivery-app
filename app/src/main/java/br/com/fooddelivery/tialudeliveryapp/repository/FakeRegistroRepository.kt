package br.com.fooddelivery.tialudeliveryapp.repository

import br.com.fooddelivery.tialudeliveryapp.models.*
import java.util.UUID

// ---------- FakeRegistroRepository + Serviço + Controller em um só ----------
class FakeRegistroRepository(
    private val currentUserId: String
) : PedidoDataSource {

    private val enderecos = mutableMapOf<String, Endereco>()
    private val estabelecimentos = mutableMapOf<String, Estabelecimento>()
    private val produtos = mutableMapOf<String, Produto>()

    init {
        enderecos["end987654321"] = Endereco(
            id = "end987654321",
            cep = "01001000",
            tipoLogradouro = "Rua",
            logradouro = "Rua Exemplo",
            numero = "123",
            bairro = "Centro",
            cidade = "São Paulo",
            estado = "SP",
            complemento = null,
            tipo = "RESIDENCIAL",
            padraoEntrega = true,
            usuarioId = currentUserId
        )

        estabelecimentos["a1b2c3d4e5f6g7h8"] = Estabelecimento(
            id = "a1b2c3d4e5f6g7h8",
            nome = "Exemplo Fantasia",
            taxaEntrega = 5.90,
            bairrosAtendidos = listOf("Centro", "Jardins", "Bela Vista")
        )

        produtos["p1r2o3d4u5t6o7"] = Produto(
            id = "p1r2o3d4u5t6o7",
            idEstabelecimento = "a1b2c3d4e5f6g7h8",
            nome = "X-Burger",
            preco = 25.90,
            descricao = "Hambúrguer clássico",
            estoque = 10,
            disponivel = true,
            imagemUrl = null
        )
        produtos["p1r2o3d4u5t6o8"] = Produto(
            id = "p1r2o3d4u5t6o8",
            idEstabelecimento = "a1b2c3d4e5f6g7h8",
            nome = "Batata Média",
            preco = 18.00,
            descricao = "Porção de batata",
            estoque = 5,
            disponivel = true,
            imagemUrl = null
        )
    }

    override suspend fun obterEndereco(id: String): Resultado<Endereco> {
        val e = enderecos[id]
        return if (e != null) Resultado.Sucesso(e) else Resultado.Erro("Endereço não encontrado")
    }

    override suspend fun obterEstabelecimento(id: String): Resultado<Estabelecimento> {
        val est = estabelecimentos[id]
        return if (est != null) Resultado.Sucesso(est) else Resultado.Erro("Estabelecimento não encontrado")
    }

    override suspend fun obterProdutos(estabelecimentoId: String, ids: List<String>): Resultado<List<Produto>> {
        val produtosSelecionados = ids.mapNotNull { produtos[it] }
        if (produtosSelecionados.size != ids.size) {
            return Resultado.Erro("Um ou mais produtos não foram encontrados")
        }
        val produtosDoEstabelecimento = produtosSelecionados.filter { it.idEstabelecimento == estabelecimentoId }
        return if (produtosDoEstabelecimento.size == ids.size) {
            Resultado.Sucesso(produtosDoEstabelecimento)
        } else {
            Resultado.Erro("Um ou mais produtos não pertencem ao estabelecimento")
        }
    }

    override suspend fun enviarPedido(request: PedidoReq): Resultado<PedidoRes> {
        val novoId = UUID.randomUUID().toString()
        return Resultado.Sucesso(PedidoRes(idPedido = novoId, status = "CREATED"))
    }

    fun decrementarEstoque(itens: List<PedidoItemReq>) {
        itens.forEach { item ->
            val p = produtos[item.idProduto]
            if (p != null) {
                produtos[item.idProduto] = p.copy(estoque = p.estoque - item.quantidade)
            }
        }
    }

    fun getCurrentUserId(): String = currentUserId
}

// ---------- Serviço com validações ----------
class PedidoService(
    private val repo: FakeRegistroRepository
) {

    suspend fun processarPedido(req: PedidoReq): ResultadoPedido {
        // CA 1.2 - Campos obrigatórios
        val camposFaltantes = mutableListOf<String>()
        if (req.idEstabelecimento.isBlank()) camposFaltantes += "idEstabelecimento"
        if (req.itens.isEmpty()) camposFaltantes += "itens"
        if (req.valorTotalEnviado <= 0.0) camposFaltantes += "valorTotalEnviado"
        if (req.idEnderecoEntrega.isBlank()) camposFaltantes += "idEnderecoEntrega"
        if (camposFaltantes.isNotEmpty()) {
            return ResultadoPedido.Erro(
                ErroRes(
                    codigoErro = "MISSING_REQUIRED_FIELDS",
                    mensagem = "Campos obrigatórios ausentes: ${camposFaltantes.joinToString(", ")}",
                    httpStatus = 400
                )
            )
        }

        // CA 1.3 - Endereço válido
        val enderecoRes = repo.obterEndereco(req.idEnderecoEntrega)
        val endereco = (enderecoRes as? Resultado.Sucesso)?.dado
        if (endereco == null || endereco.usuarioId != repo.getCurrentUserId()) {
            return ResultadoPedido.Erro(
                ErroRes(
                    codigoErro = "ADDRESS_NOT_FOUND",
                    mensagem = "O ID de endereço de entrega '${req.idEnderecoEntrega}' não foi encontrado ou não pertence à sua conta.",
                    acaoSugerida = "Verifique se o endereço ainda está salvo em sua carteira ou utilize um ID válido.",
                    httpStatus = 404
                )
            )
        }

        // CA 1.4 - Cobertura de entrega
        val est = (repo.obterEstabelecimento(req.idEstabelecimento) as? Resultado.Sucesso)?.dado
            ?: return ResultadoPedido.Erro(
                ErroRes(
                    "ESTABLISHMENT_NOT_FOUND",
                    "Estabelecimento não encontrado.",
                    httpStatus = 404
                )
            )
        val cobre = est.bairrosAtendidos.any { it.equals(endereco.bairro, ignoreCase = true) }
        if (!cobre) {
            return ResultadoPedido.Erro(
                ErroRes(
                    codigoErro = "DELIVERY_OUT_OF_AREA",
                    mensagem = "O estabelecimento não realiza entregas para o endereço vinculado ao ID '${req.idEnderecoEntrega}'.",
                    detalhes = mapOf(
                        "estabelecimento" to est.nome,
                        "cepDoEndereco" to endereco.cep
                    ),
                    httpStatus = 422
                )
            )
        }

        // CA 1.5 - Itens do estabelecimento
        val idsProdutos = req.itens.map { it.idProduto }
        val produtos = (repo.obterProdutos(req.idEstabelecimento, idsProdutos) as? Resultado.Sucesso)?.dado
            ?: return ResultadoPedido.Erro(
                ErroRes(
                    "PRODUCTS_NOT_FOUND_OR_MISMATCH",
                    "Itens não pertencem ao estabelecimento ou não foram encontrados.",
                    httpStatus = 400
                )
            )

        // CA 1.7 - Estoque
        val indisponivel = req.itens.firstOrNull { item ->
            val p = produtos.find { it.id == item.idProduto } ?: return@firstOrNull true
            !p.disponivel || p.estoque < item.quantidade
        }
        if (indisponivel != null) {
            return ResultadoPedido.Erro(
                ErroRes(
                    "OUT_OF_STOCK",
                    "Produto '${indisponivel.idProduto}' sem estoque suficiente.",
                    httpStatus = 409
                )
            )
        }

        // CA 1.6 - Validação de preço
        val subtotal = req.itens.sumOf { it.quantidade * it.precoUnitarioMomentoCompra }
        val descontoAplicado = calcularDesconto(req.desconto, subtotal)
        val totalRecalculado = (subtotal - descontoAplicado) + est.taxaEntrega
        // Comparação em centavos para evitar problemas de arredondamento
        if ((totalRecalculado * 100).toInt() != (req.valorTotalEnviado * 100).toInt()) {
            return ResultadoPedido.Erro(
                ErroRes(
                    codigoErro = "TOTAL_MISMATCH",
                    mensagem = "Divergência no total. Calculado: %.2f, Enviado: %.2f"
                        .format(totalRecalculado, req.valorTotalEnviado),
                    httpStatus = 422
                )
            )
        }

        // CA 1.1 - Sucesso
        return when (val envio = repo.enviarPedido(req)) {
            is Resultado.Sucesso -> {
                repo.decrementarEstoque(req.itens)
                ResultadoPedido.Sucesso(envio.dado.idPedido)
            }

            is Resultado.Erro -> ResultadoPedido.Erro(
                ErroRes("ORDER_CREATION_FAILED", envio.mensagem, httpStatus = 500)
            )
        }
    }

    private fun calcularDesconto(desconto: Desconto?, subtotal: Double): Double {
        if (desconto == null) return 0.0

        return when (desconto.tipoDesconto.uppercase()) {
            "FIXO" -> desconto.valorDesconto.coerceAtLeast(0.0).coerceAtMost(subtotal)
            "PERCENTUAL" -> {
                val perc = desconto.valorDesconto.coerceIn(0.0, 100.0)
                (subtotal * perc / 100.0).coerceAtMost(subtotal)
            }
            else -> 0.0 // ou lançar exceção se quiser
        }
    }
}
