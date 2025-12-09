package br.com.fooddelivery.tialudeliveryapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fooddelivery.tialudeliveryapp.ui.product.ProductRepository

private val BrandOrange = Color(0xFFFF9F1C)
private val BrandBg = Color(0xFFFBFBFB)

@Composable
fun HomeScreen(
    onNavigateToProductRegister: () -> Unit,
    onLogout: () -> Unit
) {
    val productList by ProductRepository.products.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToProductRegister,
                containerColor = BrandOrange,
                contentColor = Color.White
            ) {
                Icon(Icons.Rounded.Add, contentDescription = "Cadastrar Produto")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BrandBg)
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Olá, Lucas", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    Text(text = "O que você quer pedir hoje?", fontSize = 16.sp, color = Color.Gray)
                }

                IconButton(onClick = onLogout) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ExitToApp,
                        contentDescription = "Sair",
                        tint = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            SearchBarMock()
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Cardápio Atualizado", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                if (productList.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhum produto cadastrado ainda.\nClique no + para adicionar.",
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 20.dp)
                        )
                    }
                }
                items(productList) { product ->
                    ProductCardMock(name = product.nome, price = product.preco)
                }
            }
        }
    }
}

@Composable
fun SearchBarMock() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFFF0F0F0), RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Rounded.Search, contentDescription = null, tint = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Buscar...", color = Color.Gray)
    }
}

@Composable
fun ProductCardMock(name: String, price: Double) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth().height(80.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = name, fontWeight = FontWeight.Bold)
                Text(
                    text = "R$ ${String.format("%.2f", price)}",
                    color = BrandOrange,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}