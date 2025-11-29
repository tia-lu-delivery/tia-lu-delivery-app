package br.com.fooddelivery.tialudeliveryapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fooddelivery.tialudeliveryapp.models.Desconto
import br.com.fooddelivery.tialudeliveryapp.models.PedidoItemReq
import br.com.fooddelivery.tialudeliveryapp.models.PedidoReq
import br.com.fooddelivery.tialudeliveryapp.models.ResultadoPedido
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme
import br.com.fooddelivery.tialudeliveryapp.viewmodel.PedidoRegistroViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

private val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaNovoPedido(
    viewModel: PedidoRegistroViewModel,
    titulo: String = "Novo Pedido",
    onBack: () -> Unit = {},
    onPedidoCriado: (String) -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val estado by viewModel.estado.collectAsState()

    LaunchedEffect(estado) {
        when (val s = estado) {
            is ResultadoPedido.Sucesso -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Pedido criado: ${s.pedidoId}")
                    onPedidoCriado(s.pedidoId)
                }
            }
            is ResultadoPedido.Erro -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(s.erro.mensagem)
                }
            }
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        titulo,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = typography.bodyLarge,
                        color = colors.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = colors.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.primary,
                    titleContentColor = colors.onPrimary
                )
            )
        },
        containerColor = colors.background,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = colors.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Realizar Pedido",
                        style = typography.titleMedium,
                        color = colors.onSurface
                    )
                    Spacer(Modifier.height(16.dp))

                    FormularioNovoPedido(
                        viewModel = viewModel,
                        onConfirmar = { req -> viewModel.registrarPedido(req) }
                    )
                }
            }
        }

        if (estado is ResultadoPedido.Carregando) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioNovoPedido(
    viewModel: PedidoRegistroViewModel,
    onConfirmar: (PedidoReq) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val estado by viewModel.estado.collectAsState()
    val isLoading = estado is ResultadoPedido.Carregando

    var idEstabelecimento by remember { mutableStateOf("") }
    var idEnderecoEntrega by remember { mutableStateOf("") }
    var idProduto by remember { mutableStateOf("") }
    var quantidadeTexto by remember { mutableStateOf("1") }
    var precoUnitarioTexto by remember { mutableStateOf("0,00") }
    var taxaEntregaTexto by remember { mutableStateOf("0,00") }
    var cupom by remember { mutableStateOf("") }
    var tipoDesconto by remember { mutableStateOf("FIXO") }
    var dropdownExpanded by remember { mutableStateOf(false) }
    val tipos = listOf("FIXO", "PERCENTUAL")
    var valorDescontoTexto by remember { mutableStateOf("0,00") }
    var observacoes by remember { mutableStateOf("") }

    fun parseCurrency(text: String): Double =
        text.replace(".", "").replace(",", ".").trim().toDoubleOrNull() ?: 0.0

    val quantidade = quantidadeTexto.toIntOrNull() ?: 0
    val precoUnitario = parseCurrency(precoUnitarioTexto)
    val taxaEntrega = parseCurrency(taxaEntregaTexto)
    val valorDesconto = parseCurrency(valorDescontoTexto)
    val subtotal = precoUnitario * quantidade
    val descontoAplicado = when (tipoDesconto.uppercase(Locale.forLanguageTag("pt-BR"))) {
        "FIXO" -> if (cupom.isBlank()) 0.0 else valorDesconto
        "PERCENTUAL" -> if (cupom.isBlank()) 0.0 else subtotal * (valorDesconto / 100.0)
        else -> 0.0
    }
    val totalFinal = (subtotal - descontoAplicado) + taxaEntrega

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = idEstabelecimento,
                onValueChange = { idEstabelecimento = it },
                label = { Text("Estabelecimento") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                enabled = !isLoading
            )
            OutlinedTextField(
                value = idEnderecoEntrega,
                onValueChange = { idEnderecoEntrega = it },
                label = { Text("Endereço") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                enabled = !isLoading
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = idProduto,
                onValueChange = { idProduto = it },
                label = { Text("Produto") },
                modifier = Modifier.width(140.dp),
                singleLine = true,
                enabled = !isLoading
            )
            OutlinedTextField(
                value = quantidadeTexto,
                onValueChange = { quantidadeTexto = it.filter { ch -> ch.isDigit() } },
                label = { Text("Qtd") },
                modifier = Modifier.width(80.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = !isLoading
            )
            OutlinedTextField(
                value = precoUnitarioTexto,
                onValueChange = { precoUnitarioTexto = it.filter { ch -> ch.isDigit() || ch == '.' || ch == ',' } },
                label = { Text("Preço R$") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = !isLoading
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = taxaEntregaTexto,
                onValueChange = { taxaEntregaTexto = it.filter { ch -> ch.isDigit() || ch == '.' || ch == ',' } },
                label = { Text("Taxa R$") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = !isLoading
            )
            OutlinedTextField(
                value = cupom,
                onValueChange = { cupom = it.uppercase(Locale.forLanguageTag("pt-BR")).filter { ch -> ch.isLetterOrDigit() } },
                label = { Text("Cupom") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                enabled = !isLoading
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = tipoDesconto,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { dropdownExpanded = !dropdownExpanded }, modifier = Modifier.size(24.dp)) {
                            Icon(
                                imageVector = if (dropdownExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                contentDescription = "Selecionar tipo"
                            )
                        }
                    },
                    label = { Text("Tipo") },
                    textStyle = typography.bodySmall,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = cupom.isNotBlank() && !isLoading
                )
                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tipos.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    option,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            onClick = {
                                tipoDesconto = option
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = valorDescontoTexto,
                onValueChange = { valorDescontoTexto = it.filter { ch -> ch.isDigit() || ch == '.' || ch == ',' } },
                label = { Text("Valor desconto") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = cupom.isNotBlank() && !isLoading
            )
        }

        OutlinedTextField(
            value = observacoes,
            onValueChange = { observacoes = it },
            label = { Text("Observações") },
            modifier = Modifier.fillMaxWidth().height(96.dp),
            enabled = !isLoading
        )

        ElevatedCard(
            modifier = Modifier.fillMaxWidth().weight(1f),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = colors.surface)
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Subtotal", style = typography.bodyMedium, color = colors.onSurface)
                    Text(currencyFormatter.format(subtotal), style = typography.bodyMedium, color = colors.onSurface)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Desconto", style = typography.bodySmall, color = colors.onSurfaceVariant)
                    val descontoText = if (descontoAplicado > 0.0) "- ${currencyFormatter.format(descontoAplicado)}" else currencyFormatter.format(0.0)
                    Text(descontoText, style = typography.bodySmall, color = colors.onSurfaceVariant)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Taxa", style = typography.bodySmall, color = colors.onSurfaceVariant)
                    Text(currencyFormatter.format(taxaEntrega), style = typography.bodySmall, color = colors.onSurfaceVariant)
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = colors.onSurface.copy(alpha = 0.12f))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total", style = typography.titleMedium, color = colors.onSurface)
                    Text(currencyFormatter.format(totalFinal), style = typography.titleMedium, color = colors.primary)
                }
            }
        }

        Button(
            onClick = {
                val itens = listOf(PedidoItemReq(idProduto = idProduto, quantidade = quantidade, precoUnitarioMomentoCompra = precoUnitario))
                val desconto = if (cupom.isNotBlank()) Desconto(codigoCupom = cupom, valorDesconto = valorDesconto, tipoDesconto = tipoDesconto.uppercase(Locale.forLanguageTag("pt-BR"))) else null
                val req = PedidoReq(idEstabelecimento = idEstabelecimento, idEnderecoEntrega = idEnderecoEntrega, itens = itens, valorTotalEnviado = totalFinal, observacoesGerais = observacoes.ifBlank { null }, desconto = desconto)
                onConfirmar(req)
            },
            modifier = Modifier.fillMaxWidth()
                .height(42.dp),
            enabled = idEstabelecimento.isNotBlank() && idEnderecoEntrega.isNotBlank() && idProduto.isNotBlank() && quantidade > 0 && precoUnitario > 0.0 && !isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = colors.primary, contentColor = colors.onPrimary)
        ) {
            Text("Confirmar Pedido", style = typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaNovoPedidoPreview() {
    TiaLuDeliveryAppTheme {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Preview: Formulário Novo Pedido")
            }
        }
    }
}