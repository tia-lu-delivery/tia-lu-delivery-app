package br.com.fooddelivery.tialudeliveryapp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fooddelivery.tialudeliveryapp.ui.theme.OrangePrimary
import br.com.fooddelivery.tialudeliveryapp.ui.theme.PurpleGrey80
import br.com.fooddelivery.tialudeliveryapp.viewmodel.OrderListViewModel
import br.com.fooddelivery.tialudeliveryapp.model.OrderStatus
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.fooddelivery.tialudeliveryapp.model.Order
import br.com.fooddelivery.tialudeliveryapp.model.OrderItem
import br.com.fooddelivery.tialudeliveryapp.data.repository.mockData
@Preview(showBackground = true)
@Composable
fun OrderDetailsPreview() {
    MaterialTheme {
        OrderListScreen( )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListScreen(allOrders: List<Order> = mockData, onOrderClick : (String) -> Unit = {}) {
    val pageSize = 5
    var currentPage by remember { mutableIntStateOf(1) }

    val totalOrders = allOrders.size
    val maxPage = (totalOrders + pageSize - 1) / pageSize

    val paginatedOrders = remember(currentPage, allOrders) {
        val start = (currentPage - 1) * pageSize
        val end = (start + pageSize).coerceAtMost(totalOrders)
        allOrders.subList(start, end)
    }

    Scaffold(
        topBar = { OrderListTopAppBar() },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                tonalElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        FilledIconButton(
                            onClick = { if (currentPage > 1) currentPage-- },
                            enabled = currentPage > 1,
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = OrangePrimary,
                                contentColor = Color.White
                            )
                        ) {
                            Row { Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Página anterior"
                            ) }

                        }
                        Text(
                            text = "$currentPage /$maxPage",
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                        FilledIconButton(
                            onClick = { if (currentPage < maxPage) currentPage++ },
                            enabled = currentPage < maxPage,
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = OrangePrimary,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Próxima página"
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            LazyColumn(modifier = Modifier.weight(1f))
            {
                items(paginatedOrders) { pedido ->
                    OrderCard(pedido, {})
                }
            }
        }
    }
}

//Barra Superior da Página
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListTopAppBar() {
    CenterAlignedTopAppBar(
        title = {Text("Meus Pedidos", fontWeight = FontWeight.Bold)},
        navigationIcon = {
            IconButton(onClick = {/*Nav*/}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.White,
                    )
            }},
        actions = {
            BadgedBox(
                badge = {
                    Badge (
                        containerColor = Color.White,
                        modifier = Modifier.offset((-7).dp, 4.dp)
                    ){
                        Text("2", fontSize = 10.sp)
                    }
                }
            ) {
                IconButton(onClick = {/*Nav*/}){
                    Icon(
                        imageVector = Icons. Outlined.ShoppingCart,
                        contentDescription = "Carinho",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }
            } },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor =  OrangePrimary,
            titleContentColor = Color.White
        )
    )
}


//Cars de Pedido
@SuppressLint("DefaultLocale")
@Composable
fun OrderCard(order: Order, onClick: () -> Unit) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column (modifier = Modifier.weight(1f)){
                Text(
                    text = "Pedido ${order.orderNumber}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.Gray
                )
                Text(
                    text = order.openingTime,
                    fontSize = 14.sp,
                    color = Color.LightGray,
                )
                Column (modifier = Modifier.padding(top = 10.dp)){
                    Text(
                        text = order.restaurantName,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "R$ ${String.format("%.2f",order.totalValue)}",
                        fontWeight = FontWeight.SemiBold,
                        color = OrangePrimary
                    )
                }
            }
            Column (
                modifier = Modifier.weight(0.7f),
                horizontalAlignment = Alignment.End,
            ) {
                OrderStatusLabel(order.status)
                IconButton(onClick = {/*Nav*/}, modifier = Modifier.padding(top = 20.dp)){
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Abrir Pedido",
                        modifier = Modifier.size(24.dp),
                        tint = OrangePrimary
                    )
                }
            }

        }
    }
}
//Label de Sattus
@Composable
fun OrderStatusLabel(status: OrderStatus){
    val (text, color) = when(status){
        OrderStatus.FAZENDO, OrderStatus.ACEITO -> Pair("Em Preparação", OrangePrimary)
        OrderStatus.SAIU_PARA_ENTREGA -> Pair("Em Rota de Entrega", Color(0xFFFFC107))
        OrderStatus.FEITO -> Pair("Feito", Color(0xFFFFC107))
        OrderStatus.ENTREGUE -> Pair("Entregue", Color(0xFF4CAF50))
        OrderStatus.ABERTO -> Pair("Aberto", Color.Blue)
        OrderStatus.CANCELADO -> Pair("Pedido Cancelado" , Color(0xFFF44336))
    }
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = color.copy(alpha = 0.15f),
        shadowElevation = 0.dp
    ) {
        Text(
            text = text,
            color = color,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}