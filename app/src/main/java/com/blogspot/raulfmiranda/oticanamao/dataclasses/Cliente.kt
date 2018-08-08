package com.blogspot.raulfmiranda.oticanamao.dataclasses

import java.io.Serializable

data class Cliente(
        var nome: String,
        var endereco: String,
        var fone1: String,
        var fone2: String,
        var email: String,
        var localConta: String,
        var caminhoFotoConsulta: String,
        var caminhoFotoDocumento: String,
        var dataRecebimentoOculos: String,
        var nomeRecebedorOculos: String,
        var dataUltimaConsulta: String
): Serializable