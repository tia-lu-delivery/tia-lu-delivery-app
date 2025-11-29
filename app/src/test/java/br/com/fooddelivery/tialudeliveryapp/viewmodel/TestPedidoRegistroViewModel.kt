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

    /** Helpers **/
    private fun pedidoReq(
        idEstabelecimento: String = "est1",
        valorTotal: Double = 22.0,
        idEndereco: String = "end1",
        itens: List<PedidoItemReq> = listOf(PedidoItemReq("prod1", 2, 10.0)),
        desconto: Desconto? = null
    ) = PedidoReq(
        idEstabelecimento = idEstabelecimento,
        valorTotalEnviado = valorTotal,
        observacoesGerais = null,
        idEnderecoEntrega = idEndereco,
        itens = itens,
        desconto = desconto
    )

    private fun mockEndereco(usuario: String = usuarioId, bairro: String = "Centro") =
        Endereco("end1", "44000-000", "Rua", "Flores", "123", bairro, "Feira", "BA", null, "Residencial", true, usuario)

    private fun mockEstabelecimento(bairros: List<String> = listOf("Centro")) =
        Estabelecimento("est1", "Pizzaria", 2.0, bairros)

    private fun mockProduto(estabelecimentoId: String = "est1", id: String = "prod1") =
        Produto(id, estabelecimentoId, "Pizza", 10.0, "desc", 5, true, null)

    private fun registrarEObterEstado(req: PedidoReq): ResultadoPedido {
        viewModel.registrarPedido(req)
        testDispatcher.scheduler.advanceUntilIdle()
        return viewModel.estado.value
    }

    /*** TESTES ***/
    @Test
    fun `fluxo de sucesso retorna Sucesso`() = runTest {
        val req = pedidoReq()
        coEvery { dataSource.obterEndereco("end1") } returns Resultado.Sucesso(mockEndereco())
        coEvery { dataSource.obterEstabelecimento("est1") } returns Resultado.Sucesso(mockEstabelecimento())
        coEvery { dataSource.obterProdutos("est1", listOf("prod1")) } returns Resultado.Sucesso(listOf(mockProduto()))
        coEvery { dataSource.enviarPedido(req) } returns Resultado.Sucesso(PedidoRes("pedido123", "CRIADO"))

        val estado = registrarEObterEstado(req)
        assertTrue(estado is ResultadoPedido.Sucesso)
        assertEquals("pedido123", estado.pedidoId)
    }

    @Test
    fun `pedido sem idEstabelecimento retorna MISSING_FIELD`() = runTest {
        val estado = registrarEObterEstado(pedidoReq(idEstabelecimento = ""))
        assertEquals("MISSING_FIELD", (estado as ResultadoPedido.Erro).erro.codigoErro)
    }

    @Test
    fun `pedido com endereco inexistente retorna ADDRESS_NOT_FOUND`() = runTest {
        val req = pedidoReq()
        coEvery { dataSource.obterEndereco("end1") } returns Resultado.Erro("Endereço não encontrado")

        val estado = registrarEObterEstado(req)
        assertEquals("ADDRESS_NOT_FOUND", (estado as ResultadoPedido.Erro).erro.codigoErro)
    }

    @Test
    fun `pedido com bairro fora da area retorna DELIVERY_OUT_OF_AREA`() = runTest {
        val req = pedidoReq()
        coEvery { dataSource.obterEndereco("end1") } returns Resultado.Sucesso(mockEndereco(bairro = "Sobradinho"))
        coEvery { dataSource.obterEstabelecimento("est1") } returns Resultado.Sucesso(mockEstabelecimento(listOf("Centro")))

        val estado = registrarEObterEstado(req)
        assertEquals("DELIVERY_OUT_OF_AREA", (estado as ResultadoPedido.Erro).erro.codigoErro)
    }

    @Test
    fun `pedido com valor divergente retorna TOTAL_MISMATCH`() = runTest {
        val req = pedidoReq(valorTotal = 50.0)
        coEvery { dataSource.obterEndereco("end1") } returns Resultado.Sucesso(mockEndereco())
        coEvery { dataSource.obterEstabelecimento("est1") } returns Resultado.Sucesso(mockEstabelecimento())
        coEvery { dataSource.obterProdutos("est1", listOf("prod1")) } returns Resultado.Sucesso(listOf(mockProduto()))

        val estado = registrarEObterEstado(req)
        assertEquals("TOTAL_MISMATCH", (estado as ResultadoPedido.Erro).erro.codigoErro)
    }

    @Test
    fun `falha no envio retorna ORDER_CREATION_FAILED`() = runTest {
        val req = pedidoReq()
        coEvery { dataSource.obterEndereco("end1") } returns Resultado.Sucesso(mockEndereco())
        coEvery { dataSource.obterEstabelecimento("est1") } returns Resultado.Sucesso(mockEstabelecimento())
        coEvery { dataSource.obterProdutos("est1", listOf("prod1")) } returns Resultado.Sucesso(listOf(mockProduto()))
        coEvery { dataSource.enviarPedido(req) } returns Resultado.Erro("Falha no envio")

        val estado = registrarEObterEstado(req)
        assertEquals("ORDER_CREATION_FAILED", (estado as ResultadoPedido.Erro).erro.codigoErro)
    }
}
