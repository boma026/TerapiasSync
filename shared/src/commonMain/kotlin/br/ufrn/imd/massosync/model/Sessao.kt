package br.ufrn.imd.massosync.model

import kotlinx.serialization.Serializable

@Serializable
data class Sessao(
    val id: String,
    val clienteId: String,
    val tecnica: String,
    val data: String,
    val observacoes: String = ""
)
