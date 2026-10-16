package br.ufrn.imd.massosync.data

import androidx.compose.runtime.mutableStateListOf
import br.ufrn.imd.massosync.model.Cliente
import br.ufrn.imd.massosync.model.Sessao
import br.ufrn.imd.massosync.model.Tecnica

class MassoRepository {
    private val clientes = mutableStateListOf(
        Cliente(id = "1", nome = "Maria Silva", telefone = "(11) 98765-4321"),
        Cliente(id = "2", nome = "João Souza", telefone = "(11) 91234-5678"),
        Cliente(id = "3", nome = "Ana Costa", telefone = "(21) 99999-0000")
    )

    private val sessoes = mutableStateListOf(
        Sessao(
            id = "101",
            clienteId = "1",
            tecnica = Tecnica.MASSAGEM_RELAXANTE.titulo,
            data = "10/10/2026",
            observacoes = "Alívio de dores e tensão na região lombar."
        ),
        Sessao(
            id = "102",
            clienteId = "1",
            tecnica = Tecnica.TERMOTERAPIA.titulo,
            data = "02/10/2026",
            observacoes = "Aplicação de calor nos ombros e trapézio."
        ),
        Sessao(
            id = "103",
            clienteId = "3",
            tecnica = Tecnica.REFLEXOLOGIA_PODAL.titulo,
            data = "01/10/2026",
            observacoes = "Estímulo a pontos de relaxamento e combate à insônia."
        )
    )

    fun listarClientes(): List<Cliente> = clientes.toList()

    fun buscarClientePorId(id: String): Cliente? {
        return clientes.firstOrNull { it.id == id }
    }

    fun listarSessoesPorCliente(clienteId: String): List<Sessao> {
        return sessoes.filter { it.clienteId == clienteId }.reversed()
    }

    fun buscarUltimaSessao(clienteId: String): Sessao? {
        return sessoes.lastOrNull { it.clienteId == clienteId }
    }

    fun adicionarCliente(nome: String, telefone: String): Cliente {
        val novoId = (clientes.size + 1).toString()
        val novoCliente = Cliente(id = novoId, nome = nome.trim(), telefone = telefone.trim())
        clientes.add(novoCliente)
        return novoCliente
    }

    fun adicionarSessao(
        clienteId: String,
        tecnica: String,
        data: String,
        observacoes: String
    ): Sessao {
        val novoId = (sessoes.size + 101).toString()
        val novaSessao = Sessao(
            id = novoId,
            clienteId = clienteId,
            tecnica = tecnica,
            data = data.trim(),
            observacoes = observacoes.trim()
        )
        sessoes.add(novaSessao)
        return novaSessao
    }
}
