package br.com.fooddelivery.tialudeliveryapp.network

import br.com.fooddelivery.tialudeliveryapp.network.model.ProductDetailRequest
import br.com.fooddelivery.tialudeliveryapp.network.model.ProductDetailResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

object KtorHttpClient {

    private const val BASE_URL = "http://192.168.0.215:3333"

    val client = HttpClient(Android) {
        install(Logging)
        install(ContentNegotiation){
            json()
        }
    }

    suspend fun getProductDetail(data: ProductDetailRequest): Result<ProductDetailResponse> {
        return requireGet(url = "$BASE_URL/api/v1/merchant/R1001/products/${data.productId}")
    }

    private suspend inline fun <reified T> requireGet(
        url: String
    ): Result<T> {
        return try {
            Result.success(
                client.get(url).body()
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}