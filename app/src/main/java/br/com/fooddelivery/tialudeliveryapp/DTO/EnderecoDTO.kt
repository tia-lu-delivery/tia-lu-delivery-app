package br.com.fooddelivery.tialudeliveryapp.DTO

data class EnderecoDTO(
    val id_endereco: String,
    val cep: String,
    val tipo_logradouro: String,
    val logradouro: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val complemento: String?,
    val tipo: String,
    val padrao_entrega: Boolean
)