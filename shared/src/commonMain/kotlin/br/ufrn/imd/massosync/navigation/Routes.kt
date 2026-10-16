package br.ufrn.imd.massosync.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object Clientes : Routes

    @Serializable
    data object CadastroCliente : Routes

    @Serializable
    data class DetalheCliente(val clienteId: String) : Routes

    @Serializable
    data class NovaSessao(val clienteId: String) : Routes
}

object DeepLinkConfig {
    const val SCHEME = "massosync"
    const val HOST = "cliente"
    const val CLIENTE_BASE_URL = "$SCHEME://$HOST"
}
