package com.exemplo.enderecos.controller

import com.exemplo.enderecos.model.ErroResponse
import com.exemplo.enderecos.model.DetalheErro
import com.exemplo.enderecos.service.EnderecoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.Claims

@RestController
@RequestMapping("/api/v1/users/address")
class EnderecoController(
    private val service: EnderecoService
) {

    @GetMapping
    fun listarEnderecos(@RequestHeader("Authorization", required = false) token: String?): ResponseEntity<Any> {
        if (token.isNullOrBlank() || !token.startsWith("Bearer ") || !validarTokenJWT(token)) {
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

        return ResponseEntity.ok(response)  // Agora, substitua `Any` por um tipo específico (Exemplo: ResponseEntity<List<EnderecoDto>>)
    }

    private fun extrairUsuarioFake(token: String): String {
        // Extrair o usuário do token (exemplo simples, substitua pela lógica real)
        return "user_123"
    }

    // Função para validar o JWT
    private fun validarTokenJWT(token: String): Boolean {
        val chaveSecreta = "sua_chave_secreta" // Substitua pela chave secreta que você usa para assinar os tokens

        try {
            // Validando a assinatura e decodificando o token
            val claims: Claims = Jwts.parser()
                .setSigningKey(chaveSecreta)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .body
            // Se chegou aqui, o token é válido
            return true
        } catch (e: Exception) {
            // Em caso de erro, o token não é válido
            return false
        }
    }
}
