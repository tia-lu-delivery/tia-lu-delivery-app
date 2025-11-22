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
                12346, "05400-000", "Avenida", "Faria Lima",
                "4509", "Pinheiros", "São Paulo", "SP",
                "Casa 2", "Residencial", false
            ),
            EnderecoMock(
                12345, "01001-000", "Rua", "Direita",
                "100", "Sé", "São Paulo", "SP",
                "Bloco A", "Comercial", true
            ),
            EnderecoMock(
                12347, "70070-100", "SQS", "402 Sul",
                "Bloco B", "Asa Sul", "Brasília", "DF",
                "Apto 302", "Residencial", false
            )
        )

        val ordenada = listaMock.sortedByDescending { it.padraoEntrega }

        val dtos = ordenada.map {
            EnderecoDto(
                idEndereco = it.id,
                cep = it.cep,
                tipoLogradouro = it.tipoLogradouro,
                logradouro = it.logradouro,
                numero = it.numero,
                bairro = it.bairro,
                cidade = it.cidade,
                estado = it.estado,
                complemento = it.complemento,
                tipo = it.tipo,
                padraoEntrega = it.padraoEntrega
            )
        }

        return EnderecosResponse(
            totalEnderecos = dtos.size,
            enderecos = dtos
        )
    }
}
