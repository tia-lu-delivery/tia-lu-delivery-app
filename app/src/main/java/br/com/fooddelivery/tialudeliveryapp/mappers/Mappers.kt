import br.com.fooddelivery.tialudeliveryapp.DTO.*
import br.com.fooddelivery.tialudeliveryapp.models.*


// Endereco
fun EnderecoDTO.toDomain() = Endereco(
    id = this.id_endereco,
    cep = this.cep,
    tipoLogradouro = this.tipo_logradouro,
    logradouro = this.logradouro,
    numero = this.numero,
    bairro = this.bairro,
    cidade = this.cidade,
    estado = this.estado,
    complemento = this.complemento,
    tipo = this.tipo,
    padraoEntrega = this.padrao_entrega,
)

fun Endereco.toDto() = EnderecoDTO(
    id_endereco = this.id,
    cep = this.cep,
    tipo_logradouro = this.tipoLogradouro,
    logradouro = this.logradouro,
    numero = this.numero,
    bairro = this.bairro,
    cidade = this.cidade,
    estado = this.estado,
    complemento = this.complemento,
    tipo = this.tipo,
    padrao_entrega = this.padraoEntrega
)

// PedidoItem
fun PedidoItemReq.toDto() = PedidoItemDTO(
    idProduto = this.idProduto,
    quantidade = this.quantidade,
    precoUnitarioMomentoCompra = this.precoUnitarioMomentoCompra
)

// Desconto
fun Desconto.toDto() = DescontoDTO(
    codigoCupom = this.codigoCupom,
    valorDesconto = this.valorDesconto,
    tipoDesconto = this.tipoDesconto
)

// Pedido
fun PedidoReq.toDto() = PedidoReqDTO(
    idEstabelecimento = this.idEstabelecimento,
    valorTotalEnviado = this.valorTotalEnviado,
    observacoesGerais = this.observacoesGerais,
    idEnderecoEntrega = this.idEnderecoEntrega,
    itens = this.itens.map { it.toDto() },
    desconto = this.desconto?.toDto()
)

// Retorno do pedido
fun PedidoResDTO.toDomain() = PedidoRes(
    idPedido = this.idPedido,
    status = this.status,

)

fun PedidoItemDTO.toDomain() = PedidoItemReq(
    idProduto = this.idProduto,
    quantidade = this.quantidade,
    precoUnitarioMomentoCompra = this.precoUnitarioMomentoCompra
)
