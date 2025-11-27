package br.com.fooddelivery.tialudeliveryapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.fooddelivery.tialudeliveryapp.models.*
import br.com.fooddelivery.tialudeliveryapp.repository.PedidoDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PedidoRegistroViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var dataSource: PedidoDataSource
    private lateinit var viewModel: PedidoRegistroViewModel
    private val usuarioId = "user123"

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        dataSource = mockk()
        viewModel = PedidoRegistroViewModel(dataSource, usuarioId)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /*** FLUXO DE SUCESSO ***/
    @Test
    fun `registrarPedido com sucesso retorna Sucesso`() = runTest {
        val pedidoReq = PedidoReq(
            idEstabelecimento = "est1",
            valorTotalEnviado = 22.0,
            observacoesGerais = "Sem cebola",
            idEnderecoEntrega = "end1",
            itens = listOf(PedidoItemReq("prod1", 2, 10.0)),
            desconto = null
        )

        val endereco = Endereco(
            id = "end1",
            cep = "44000-000",
            tipoLogradouro = "Rua",
            logradouro = "Das Flores",
            numero = "123",
            bairro = "Centro",
            cidade = "Feira de Santana",
            estado = "BA",
            complemento = null,
            tipo = "Residencial",
            padraoEntrega = true,
            usuarioId = usuarioId
        )

        val estabelecimento = Estabelecimento(
            id = "est1",
            nome = "Pizzaria do Centro",
            taxaEntrega = 2.0,
            bairrosAtendidos = listOf("Centro")
        )

        val produto = Produto(
            id = "prod1",
            idEstabelecimento = "est1",
            nome = "Pizza Calabresa",
            preco = 10.0,
            descricao = "Pizza com calabresa e queijo",
            estoque = 5,
            disponivel = true,
            imagemUrl = null
        )

        coEvery { dataSource.obterEndereco("end1") } returns Resultado.Sucesso(endereco)
        coEvery { dataSource.obterEstabelecimento("est1") } returns Resultado.Sucesso(estabelecimento)
        coEvery { dataSource.obterProdutos("est1", listOf("prod1")) } returns Resultado.Sucesso(listOf(produto))
        coEvery { dataSource.enviarPedido(pedidoReq) } returns Resultado.Sucesso(PedidoRes("pedido123", "CRIADO"))

        viewModel.registrarPedido(pedidoReq)
        testDispatcher.scheduler.advanceUntilIdle()

        val estadoFinal = viewModel.estado.value
        assertTrue(estadoFinal is ResultadoPedido.Sucesso)
        assertEquals("pedido123", estadoFinal.pedidoId)
    }

    /*** CAMPOS OBRIGATÓRIOS ***/
    @Test
    fun `pedido sem idEstabelecimento retorna MISSING_FIELD`() = runTest {
        val pedidoReq = PedidoReq(
            idEstabelecimento = "",
            valorTotalEnviado = 10.0,
            observacoesGerais = null,
            idEnderecoEntrega = "end1",
            itens = listOf(PedidoItemReq("prod1", 1, 10.0))
        )
        viewModel.registrarPedido(pedidoReq)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals("MISSING_FIELD", (viewModel.estado.value as ResultadoPedido.Erro).erro.codigoErro)
    }

    @Test
    fun `pedido sem endereco retorna MISSING_FIELD`() = runTest {
        val pedidoReq = PedidoReq(
            idEstabelecimento = "est1",
            valorTotalEnviado = 10.0,
            observacoesGerais = null,
            idEnderecoEntrega = "",
            itens = listOf(PedidoItemReq("prod1", 1, 10.0))
        )
        viewModel.registrarPedido(pedidoReq)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals("MISSING_FIELD", (viewModel.estado.value as ResultadoPedido.Erro).erro.codigoErro)
    }

    @Test
    fun `pedido sem itens retorna MISSING_FIELD`() = runTest {
        val pedidoReq = PedidoReq(
            idEstabelecimento = "est1",
            valorTotalEnviado = 10.0,
            observacoesGerais = null,
            idEnderecoEntrega = "end1",
            itens = emptyList()
        )
        viewModel.registrarPedido(pedidoReq)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals("MISSING_FIELD", (viewModel.estado.value as ResultadoPedido.Erro).erro.codigoErro)
    }

    @Test
    fun `pedido com valorTotalEnviado menor ou igual a zero retorna INVALID_TOTAL`() = runTest {
        val pedidoReq = PedidoReq(
            idEstabelecimento = "est1",
            valorTotalEnviado = 0.0,
            observacoesGerais = null,
            idEnderecoEntrega = "end1",
            itens = listOf(PedidoItemReq("prod1", 1, 10.0))
        )
        viewModel.registrarPedido(pedidoReq)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals("INVALID_TOTAL", (viewModel.estado.value as ResultadoPedido.Erro).erro.codigoErro)
    }

    /*** ENDEREÇO INVÁLIDO ***/
    @Test
    fun `pedido com endereco inexistente retorna ADDRESS_NOT_FOUND`() = runTest {
        val pedidoReq = PedidoReq(
            idEstabelecimento = "est1",
            valorTotalEnviado = 22.0,
            observacoesGerais = null,
            idEnderecoEntrega = "end1",
            itens = listOf(PedidoItemReq("prod1", 2, 10.0))
        )

        coEvery { dataSource.obterEndereco("end1") } returns Resultado.Erro("Endereço não encontrado")

        viewModel.registrarPedido(pedidoReq)
        testDispatcher.scheduler.advanceUntilIdle()

        val estadoFinal = viewModel.estado.value
        assertTrue(estadoFinal is ResultadoPedido.Erro)
        assertEquals("ADDRESS_NOT_FOUND", estadoFinal.erro.codigoErro)
    }

    /*** ENDEREÇO NÃO PERTENCE AO USUÁRIO ***/
    @Test
    fun `pedido com endereco de outro usuario retorna ADDRESS_NOT_OWNED`() = runTest {
        val pedidoReq = PedidoReq("est1", 22.0, null, "end1", listOf(PedidoItemReq("prod1", 2, 10.0)))
        val endereco = Endereco("end1", "44000-000", "Rua", "Flores", "123", "Centro", "Feira", "BA", null, "Residencial", true, usuarioId = "outroUser")

        coEvery { dataSource.obterEndereco("end1") } returns Resultado.Sucesso(endereco)

        viewModel.registrarPedido(pedidoReq)
        testDispatcher.scheduler.advanceUntilIdle()

        val estadoFinal = viewModel.estado.value
        assertTrue(estadoFinal is ResultadoPedido.Erro)
        assertEquals("ADDRESS_NOT_OWNED", estadoFinal.erro.codigoErro)
    }

    /*** BAIRRO FORA DA ÁREA DE ENTREGA ***/
    @Test
    fun `pedido com bairro fora da area retorna DELIVERY_OUT_OF_AREA`() = runTest {
        val pedidoReq = PedidoReq("est1", 22.0, null, "end1", listOf(PedidoItemReq("prod1", 2, 10.0)))
        val endereco = Endereco("end1", "44000-000", "Rua", "Flores", "123", "Sobradinho", "Feira", "BA", null, "Residencial", true, usuarioId)
        val estabelecimento = Estabelecimento("est1", "Pizzaria", 2.0, listOf("Centro"))

        coEvery { dataSource.obterEndereco("end1") } returns Resultado.Sucesso(endereco)
        coEvery { dataSource.obterEstabelecimento("est1") } returns Resultado.Sucesso(estabelecimento)

        viewModel.registrarPedido(pedidoReq)
        testDispatcher.scheduler.advanceUntilIdle()

        val estadoFinal = viewModel.estado.value
        assertTrue(estadoFinal is ResultadoPedido.Erro)
        assertEquals("DELIVERY_OUT_OF_AREA", estadoFinal.erro.codigoErro)
    }

    /*** PRODUTO DE OUTRO ESTABELECIMENTO ***/
    @Test
    fun `pedido com produto de outro estabelecimento retorna PRODUCT_NOT_IN_ESTABLISHMENT`() = runTest {
        val pedidoReq = PedidoReq("est1", 22.0, null, "end1", listOf(PedidoItemReq("prodX", 1, 10.0)))
        val endereco = Endereco("end1", "44000-000", "Rua", "Flores", "123", "Centro", "Feira", "BA", null, "Residencial", true, usuarioId)
        val estabelecimento = Estabelecimento("est1", "Pizzaria", 2.0, listOf("Centro"))
        val produto = Produto("prodX", "outroEst", "Pizza", 10.0, "desc", 5, true, null)

        coEvery { dataSource.obterEndereco("end1") } returns Resultado.Sucesso(endereco)
        coEvery { dataSource.obterEstabelecimento("est1") } returns Resultado.Sucesso(estabelecimento)
        coEvery { dataSource.obterProdutos("est1", listOf("prodX")) } returns Resultado.Sucesso(listOf(produto))

        viewModel.registrarPedido(pedidoReq)
        testDispatcher.scheduler.advanceUntilIdle()

        val estadoFinal = viewModel.estado.value
        assertTrue(estadoFinal is ResultadoPedido.Erro)
        assertEquals("PRODUCT_OUT_OF_ESTABLISHMENT", estadoFinal.erro.codigoErro)
    }

    /*** VALOR DIVERGENTE ***/
    @Test
    fun `pedido com valor divergente retorna TOTAL_MISMATCH`() = runTest {
        val pedidoReq = PedidoReq("est1", 50.0, null, "end1", listOf(PedidoItemReq("prod1", 2, 10.0)))
        val endereco = Endereco("end1", "44000-000", "Rua", "Flores", "123", "Centro", "Feira", "BA", null, "Residencial", true, usuarioId)
        val estabelecimento = Estabelecimento("est1", "Pizzaria", 2.0, listOf("Centro"))
        val produto = Produto("prod1", "est1", "Pizza", 10.0, "desc", 5, true, null)

        coEvery { dataSource.obterEndereco("end1") } returns Resultado.Sucesso(endereco)
        coEvery { dataSource.obterEstabelecimento("est1") } returns Resultado.Sucesso(estabelecimento)
        coEvery { dataSource.obterProdutos("est1", listOf("prod1")) } returns Resultado.Sucesso(listOf(produto))

        viewModel.registrarPedido(pedidoReq)
        testDispatcher.scheduler.advanceUntilIdle()

        val estadoFinal = viewModel.estado.value
        assertTrue(estadoFinal is ResultadoPedido.Erro)
        assertEquals("TOTAL_MISMATCH", estadoFinal.erro.codigoErro)
    }

    /*** FALHA NO ENVIO ***/
    @Test
    fun `falha no envio retorna SEND_FAILED`() = runTest {
        val pedidoReq = PedidoReq("est1", 22.0, null, "end1", listOf(PedidoItemReq("prod1", 2, 10.0)))
        val endereco = Endereco("end1", "44000-000", "Rua", "Flores", "123", "Centro", "Feira", "BA", null, "Residencial", true, usuarioId)
        val estabelecimento = Estabelecimento("est1", "Pizzaria", 2.0, listOf("Centro"))
        val produto = Produto("prod1", "est1", "Pizza", 10.0, "desc", 5, true, null)

        coEvery { dataSource.obterEndereco("end1") } returns Resultado.Sucesso(endereco)
        coEvery { dataSource.obterEstabelecimento("est1") } returns Resultado.Sucesso(estabelecimento)
        coEvery { dataSource.obterProdutos("est1", listOf("prod1")) } returns Resultado.Sucesso(listOf(produto))
        coEvery { dataSource.enviarPedido(pedidoReq) } returns Resultado.Erro("Falha no envio")

        viewModel.registrarPedido(pedidoReq)
        testDispatcher.scheduler.advanceUntilIdle()

        val estadoFinal = viewModel.estado.value
        assertTrue(estadoFinal is ResultadoPedido.Erro)
        assertEquals("ORDER_CREATION_FAILED", estadoFinal.erro.codigoErro)
    }

}
