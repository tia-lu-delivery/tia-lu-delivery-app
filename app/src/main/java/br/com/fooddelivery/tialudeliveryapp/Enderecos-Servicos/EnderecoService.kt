package com.exemplo.enderecos.service

import com.exemplo.enderecos.model.EnderecoDto
import com.exemplo.enderecos.model.EnderecosResponse
import com.exemplo.enderecos.model.EnderecoMock
import org.springframework.stereotype.Service

@Service
class EnderecoService {

    fun buscarEnderecos(usuarioId: String): EnderecosResponse {
        val listaMock = listOf(
            EnderecoMock(
                id = 12346,
                cep = "05400-000",
                tipoLogradouro = "Avenida",
                logradouro = "Faria Lima",
                numero = "4509",
                bairro = "Pinheiros",
                cidade = "São Paulo",
                estado = "SP",
                complemento = "Casa 2",
                tipo = "Residencial",
                padraoEntrega = false
            ),
            EnderecoMock(
                id = 12345,
                cep = "01001-000",
                tipoLogradouro = "Rua",
                logradouro = "Direita",
                numero = "100",
                bairro = "Sé",
                cidade = "São Paulo",
                estado = "SP",
                complemento = "Bloco A",
                tipo = "Comercial",
                padraoEntrega = true
            ),
            EnderecoMock(
                id = 12347,
                cep = "70070-100",
                tipoLogradouro = "SQS",
                logradouro = "402 Sul",
                numero = "Bloco B",
                bairro = "Asa Sul",
                cidade = "Brasília",
                estado = "DF",
                complemento = "Apto 302",
                tipo = "Residencial",
                padraoEntrega = false
            )
        )

        val ordenada = listaMock.sortedByDescending { it.padraoEntrega }

        val dtos = ordenada.map { endereco ->
            EnderecoDto(
                idEndereco = endereco.id,
                cep = endereco.cep,
                tipoLogradouro = endereco.tipoLogradouro,
                logradouro = endereco.logradouro,
                numero = endereco.numero,
                bairro = endereco.bairro,
                cidade = endereco.cidade,
                estado = endereco.estado,
                complemento = endereco.complemento,
                tipo = endereco.tipo,
                padraoEntrega = endereco.padraoEntrega
            )
        }

        return EnderecosResponse(
            totalEnderecos = dtos.size,
            enderecos = dtos
        )
    }
}
