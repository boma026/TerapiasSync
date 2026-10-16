package br.ufrn.imd.massosync.model

import kotlinx.serialization.Serializable

@Serializable
data class Cliente(
    val id: String,
    val nome: String,
    val telefone: String
)
