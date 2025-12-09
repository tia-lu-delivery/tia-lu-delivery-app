package br.com.fooddelivery.tialudeliveryapp.ui.product

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.RestaurantMenu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

private val BrandOrange = Color(0xFFFF9F1C)
private val BrandTextDark = Color(0xFF1A1A1A)
private val BrandCreamBg = Color(0xFFFFFBF2)
private val InputGrayBg = Color(0xFFF5F5F5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = viewModel(),
    onSaved: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            Toast.makeText(context, "Produto cadastrado!", Toast.LENGTH_SHORT).show()
            onSaved()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandCreamBg)
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Voltar", tint = BrandTextDark)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Rounded.RestaurantMenu, null, tint = BrandOrange, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Novo Produto", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = BrandTextDark)
        }

        Text("Preencha os dados do item do cardápio.", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp, bottom = 32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Produto Disponível?", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = BrandTextDark)
            Switch(
                checked = uiState.disponivel,
                onCheckedChange = { viewModel.onDisponivelChange(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = BrandOrange,
                    checkedTrackColor = BrandOrange.copy(alpha = 0.5f)
                )
            )
        }

        ProductTextField(uiState.nome, { viewModel.onNomeChange(it) }, "Nome do Produto*", keyboardCapitalization = KeyboardCapitalization.Words)

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = uiState.categoriaSelecionada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoria*") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BrandOrange, focusedLabelColor = BrandOrange,
                    unfocusedContainerColor = InputGrayBg, focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                viewModel.categoriasDisponiveis.forEach { categoria ->
                    DropdownMenuItem(
                        text = { Text(categoria) },
                        onClick = {
                            viewModel.onCategoriaChange(categoria)
                            expanded = false
                        }
                    )
                }
            }
        }

        ProductTextField(uiState.descricao, { viewModel.onDescricaoChange(it) }, "Descrição*", keyboardCapitalization = KeyboardCapitalization.Sentences, singleLine = false, maxLines = 5, modifier = Modifier.height(100.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.weight(1f)) { ProductTextField(uiState.precoUnitario, { viewModel.onPrecoChange(it) }, "Preço (R$)*", keyboardType = KeyboardType.Decimal) }
            Box(modifier = Modifier.weight(1f)) { ProductTextField(uiState.quantidadeEstoque, { viewModel.onEstoqueChange(it) }, "Estoque*", keyboardType = KeyboardType.Number) }
        }

        ProductTextField(uiState.imagemUrl, { viewModel.onImagemUrlChange(it) }, "URL da Imagem (Opcional)", keyboardType = KeyboardType.Uri)

        if (uiState.errorMessage != null) {
            Text(uiState.errorMessage!!, color = Color.Red, fontSize = 14.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(vertical = 16.dp))
        } else {
            Spacer(modifier = Modifier.height(24.dp))
        }

        Button(
            onClick = { viewModel.submitProduct() },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = BrandOrange),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Salvar Produto", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ProductTextField(value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier, keyboardType: KeyboardType = KeyboardType.Text, keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.None, singleLine: Boolean = true, maxLines: Int = 1) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange, label = { Text(label) },
        modifier = modifier.fillMaxWidth().padding(bottom = 16.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, capitalization = keyboardCapitalization),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BrandOrange, focusedLabelColor = BrandOrange, cursorColor = BrandOrange, unfocusedContainerColor = InputGrayBg, focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent),
        singleLine = singleLine, maxLines = maxLines
    )
}