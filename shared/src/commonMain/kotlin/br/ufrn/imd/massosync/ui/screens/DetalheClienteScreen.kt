package br.ufrn.imd.massosync.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.ufrn.imd.massosync.model.Cliente
import br.ufrn.imd.massosync.model.Sessao
import br.ufrn.imd.massosync.model.Tecnica
import br.ufrn.imd.massosync.ui.components.EmptyStateView
import br.ufrn.imd.massosync.ui.components.MassoTopBar
import br.ufrn.imd.massosync.ui.components.SessaoCard
import br.ufrn.imd.massosync.ui.theme.MassoSyncTheme

@Composable
fun DetalheClienteScreen(
    cliente: Cliente?,
    sessoes: List<Sessao>,
    onNovaSessaoClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    onVoltar: (() -> Unit)? = null
) {
    Scaffold(
        topBar = {
            MassoTopBar(
                titulo = cliente?.nome ?: "Detalhes do Cliente",
                onVoltar = onVoltar
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (cliente == null) {
            EmptyStateView(
                titulo = "Cliente não encontrado",
                mensagem = "O cliente solicitado não foi localizado no cadastro.",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    end = 16.dp,
                    bottom = innerPadding.calculateBottomPadding() + 24.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Dados do Cliente",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.semantics { heading() }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Nome: ${cliente.nome}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Telefone: ${cliente.telefone}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Histórico de Sessões",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.semantics { heading() }
                        )

                        Button(
                            onClick = { onNovaSessaoClick(cliente.id) },
                            modifier = Modifier.defaultMinSize(minHeight = 48.dp)
                        ) {
                            Text("+ Nova Sessão")
                        }
                    }
                }

                if (sessoes.isEmpty()) {
                    item {
                        EmptyStateView(
                            titulo = "Nenhuma sessão registrada",
                            mensagem = "Este cliente ainda não possui atendimentos no histórico."
                        )
                    }
                } else {
                    items(sessoes, key = { it.id }) { sessao ->
                        SessaoCard(sessao = sessao)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DetalheClienteComSessoesPreview() {
    val mockCliente = Cliente("1", "Maria Silva", "(11) 98765-4321")
    val mockSessoes = listOf(
        Sessao("1", "1", Tecnica.MASSAGEM_RELAXANTE.titulo, "10/10/2026", "Alívio lombar"),
        Sessao("2", "1", Tecnica.TERMOTERAPIA.titulo, "02/10/2026", "Compressa térmica")
    )
    MassoSyncTheme {
        DetalheClienteScreen(
            cliente = mockCliente,
            sessoes = mockSessoes,
            onNovaSessaoClick = {},
            onVoltar = {}
        )
    }
}

@Preview
@Composable
fun DetalheClienteSemSessoesPreview() {
    val mockCliente = Cliente("2", "João Souza", "(11) 91234-5678")
    MassoSyncTheme {
        DetalheClienteScreen(
            cliente = mockCliente,
            sessoes = emptyList(),
            onNovaSessaoClick = {},
            onVoltar = {}
        )
    }
}
