class PedidoRepository(
    private val api: PedidoApi
) : PedidoDataSource {

    override suspend fun obterEndereco(id: String): Resultado<Endereco> {
        return try {
            val dto = api.obterEndereco(id)
            Resultado.Sucesso(dto.toModel())
        } catch (e: Exception) {
            Resultado.Erro("Falha ao obter endereço: ${e.message}")
        }
    }

    override suspend fun obterEstabelecimento(id: String): Resultado<Estabelecimento> {
        return try {
            val dto = api.obterEstabelecimento(id)
            Resultado.Sucesso(dto.toModel())
        } catch (e: Exception) {
            Resultado.Erro("Falha ao obter estabelecimento: ${e.message}")
        }
    }

    override suspend fun obterProdutos(ids: List<String>): Resultado<List<Produto>> {
        return try {
            val lista = api.obterProdutos(ids).map { it.toModel() }
            Resultado.Sucesso(lista)
        } catch (e: Exception) {
            Resultado.Erro("Falha ao obter produtos: ${e.message}")
        }
    }

    override suspend fun enviarPedido(request: PedidoReq): Resultado<PedidoRes> {
        return try {
            val dto = api.enviarPedido(request.toDTO())
            Resultado.Sucesso(dto.toModel())
        } catch (e: Exception) {
            Resultado.Erro("Erro ao criar pedido: ${e.message}")
        }
    }
}
