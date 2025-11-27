import br.com.fooddelivery.tialudeliveryapp.Dto.ProdutoDTO
import br.com.fooddelivery.tialudeliveryapp.dto.EnderecoDTO
import br.com.fooddelivery.tialudeliveryapp.dto.EstabelecimentoDTO
import br.com.fooddelivery.tialudeliveryapp.dto.PedidoReqDTO
import br.com.fooddelivery.tialudeliveryapp.dto.PedidoResDTO
import br.com.fooddelivery.tialudeliveryapp.models.EstabelecimentoDTO
import retrofit2.http.*

interface PedidoApi {

    @GET("enderecos/{id}")
    suspend fun obterEndereco(@Path("id") id: String): EnderecoDTO

    @GET("estabelecimentos/{id}")
    suspend fun obterEstabelecimento(@Path("id") id: String): EstabelecimentoDTO

    @POST("produtos/lista")
    suspend fun obterProdutos(@Body ids: List<String>): List<ProdutoDTO>

    @POST("pedidos")
    suspend fun enviarPedido(@Body req: PedidoReqDTO): PedidoResDTO
}
