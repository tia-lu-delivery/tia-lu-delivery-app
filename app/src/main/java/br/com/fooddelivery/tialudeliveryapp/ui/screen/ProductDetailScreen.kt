package br.com.fooddelivery.tialudeliveryapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fooddelivery.tialudeliveryapp.ui.component.BackButton
import br.com.fooddelivery.tialudeliveryapp.ui.component.product_details.ProductMainInfo
import br.com.fooddelivery.tialudeliveryapp.ui.theme.OrangeGrey80
import br.com.fooddelivery.tialudeliveryapp.ui.theme.TiaLuDeliveryAppTheme
import br.com.fooddelivery.tialudeliveryapp.viewModel.ProductDetailScreenViewModel
import coil.compose.AsyncImage

@Composable
fun ProductDetailsScreen(modifier: Modifier = Modifier, productId: String,viewModel: ProductDetailScreenViewModel = viewModel()) {

    LaunchedEffect(key1 = Unit) {
        viewModel.loadProduct(productId = productId)
    }

    val context = LocalContext.current

    val product by viewModel.product.collectAsState()

    product.onSuccess {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(OrangeGrey80)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BackButton(
                        modifier = Modifier.shadow(
                            elevation = 12.dp,
                            shape = CircleShape
                        )
                    ) { }
                }

                AsyncImage(
                    model =  it.imageUrl,
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.CenterHorizontally)
                        .scale(1.2f)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.height(24.dp))

                ProductMainInfo(
                    productName =  it.name,
                    productDescription =  it.description,
                    productQuantity =  it.quantity,
                    productPrice = it.price
                )
            }

            Box(
                modifier = Modifier
                    .zIndex(-1f)
                    .align(Alignment.BottomStart)
                    .clip(RoundedCornerShape(topStart = 48.dp, topEnd = 46.dp))
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .background(color = Color.White)
            )
        }
    }

    product.onFailure {
        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
    }
}

@Preview
@Composable
private fun ProductDetailScreenPreview() {
    TiaLuDeliveryAppTheme() {
        ProductDetailsScreen(productId = "123")
    }
}