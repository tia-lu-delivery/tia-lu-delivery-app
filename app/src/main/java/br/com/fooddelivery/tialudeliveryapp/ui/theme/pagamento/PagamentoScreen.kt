import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TelaPagamentoCompleta(pagamentoViewModel: PagamentoViewModel = viewModel()) {

    val valor by pagamentoViewModel.valor
    val forma by pagamentoViewModel.formaPagamento
    val mensagem by pagamentoViewModel.mensagem
    val pagamentos by pagamentoViewModel.listaPagamentos

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Registrar Pagamento",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de valor
        OutlinedTextField(
            value = valor,
            onValueChange = { pagamentoViewModel.setValor(it) },
            label = { Text("Valor") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Dropdown forma de pagamento
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = forma,
                onValueChange = {},
                readOnly = true,
                label = { Text("Forma de Pagamento") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("Dinheiro", "Cartão", "Pix").forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            pagamentoViewModel.setFormaPagamento(option)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão registrar
        Button(
            onClick = { pagamentoViewModel.registrarPagamento() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }

        if (mensagem.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = mensagem,
                color = if (mensagem.contains("sucesso")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Histórico de Pagamentos",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pagamentos) { pagamento ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { pagamentoViewModel.editarPagamento(pagamento) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("R$ %.2f".format(pagamento.valor))
                            Text("Forma: ${pagamento.forma}")
                        }
                        Button(
                            onClick = { pagamentoViewModel.excluirPagamento(pagamento.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Excluir", color = MaterialTheme.colorScheme.onError)
                        }
                    }
                }
            }
        }
    }
}
