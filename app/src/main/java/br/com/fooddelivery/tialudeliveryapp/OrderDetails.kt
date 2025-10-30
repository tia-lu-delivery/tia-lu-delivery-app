package br.com.fooddelivery.tialudeliveryapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fooddelivery.tialudeliveryapp.ui.theme.OrangeLight
import br.com.fooddelivery.tialudeliveryapp.ui.theme.OrangePrimary
import br.com.fooddelivery.tialudeliveryapp.ui.theme.PurpleGrey80

enum class OrderStatus{
    PENDENTE,
    FAZENDO,
    FEITO,
    SAIU_ENTREGA,
    ENTREGUE
}

data class OrderItem(
    val id: String,
    val name: String,
    val quantity: Int,
    val price: Double
)

data class Order(
    val orderNumber: String,
    val openingTime: String,
    val status: OrderStatus,
    val customerName: String,
    val customerPhone: String,
    val deliveryAddress: String,
    val items: List<OrderItem>
)

@Preview(showBackground = true)
@Composable
fun OrderDetailsPreview() {
    MaterialTheme {
        OrderDetailsScreen( )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreen( ){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Pedido") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OrangePrimary,
                    titleContentColor = Color.White)
                )
        }
    ){ paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OrderHeader(
                    "123",
                    "10:20",
                    OrderStatus.PENDENTE
                )
            }
            item { CustomerInfoCard(
                "Maria Silva",
                "(75) 98754-8585"
            ) }
            item { AddressCard("Morada nobre, 75, Apto 75B, Rua C  Ipira-BA") }
            item {
                Text(
                    text = "Itens do Pedido",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }
            item { OrderItem()}
            item { OrderItem()}
            item { OrderItem()}
            item { OrderTotal()}
            item { ActionButton() }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun OrderHeader( orderNumber: String, openingTime: String, status: OrderStatus) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Pedido #$orderNumber",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Aberto às $openingTime",
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = Color(0xFF666666)
            )
        }

        OrderStatusCard(status = status)
    }
}

@Composable
fun CustomerInfoCard( customerName: String, customerPhone: String){
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ){
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "CLIENTE",
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Nome do cliente",
                    tint = Color(0xFFFF8A00),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = customerName,
                )
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Telefone do cliente",
                    tint = Color(0xFFFF8A00),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = customerPhone,
                )
            }

        }
    }
}

@Composable
fun AddressCard(address: String){
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "ENDEREÇO DE ENTREGA",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Endereço",
                    tint = Color(0xFFFF8A00),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(top = 2.dp)
                )
                Text(
                    text = address
                )
            }
        }
    }
}

@Composable
fun OrderItem() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFFFD8AB),
                    shadowElevation = 1.dp
                ) {
                    Text(
                        text = "3x",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Text(
                    text = "Pizza",
                    fontWeight = FontWeight.Medium
                )
            }

            Text(
                text = "R$ 50",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}

@Composable
fun OrderTotal(){
    Column ( modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total do Pedido",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "R$ 150",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun ActionButton() {
    Button(
        onClick = {  },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = OrangePrimary,
            disabledContainerColor = PurpleGrey80
        ),
        shape = RoundedCornerShape(28.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(
            text = "Aceitar Pedido",
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun OrderStatusCard(status: OrderStatus){
    val text = when (status){
        OrderStatus.PENDENTE -> "Pendente"
        OrderStatus.FAZENDO -> "Fazendo"
        OrderStatus.FEITO ->  "Feito"
        OrderStatus.SAIU_ENTREGA ->  "Saiu p/ Entrega"
        OrderStatus.ENTREGUE ->  "Entregue"
    }
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = OrangePrimary,
        shadowElevation = 2.dp
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}


// confirmação antes de mudar o status do pedido
@Composable
fun ConfirmationDialog(){}