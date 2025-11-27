package com.exemplo.enderecos.controller

import com.exemplo.enderecos.model.ErroResponse
import com.exemplo.enderecos.model.DetalheErro
import com.exemplo.enderecos.service.EnderecoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users/address")
class EnderecoController(
    private val service: EnderecoService
) {

    @GetMapping
    fun listarEnderecos(
        @RequestHeader("Authorization", required = false) token: String?
    ): ResponseEntity<Any> {

        if (token.isNullOrBlank() || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErroResponse(
                    erro = DetalheErro(
                        codigo = "NAO_AUTORIZADO",
                        detalhe = "Token ausente ou inválido. Realize login novamente."
                    )
                )
            )
        }

        val usuarioId = extrairUsuarioFake(token)

        val response = service.buscarEnderecos(usuarioId)

        return ResponseEntity.ok(response)
    }

    private fun extrairUsuarioFake(token: String): String {
        return "user_123"
    }
}
