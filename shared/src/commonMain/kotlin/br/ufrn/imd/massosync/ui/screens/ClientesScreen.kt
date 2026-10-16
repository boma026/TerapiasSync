package br.ufrn.imd.massosync.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.ufrn.imd.massosync.model.Cliente
import br.ufrn.imd.massosync.ui.components.ClienteCard
import br.ufrn.imd.massosync.ui.components.EmptyStateView
import br.ufrn.imd.massosync.ui.components.MassoTopBar
import br.ufrn.imd.massosync.ui.theme.MassoSyncTheme

@Composable
fun ClientesScreen(
    clientes: List<Cliente>,
    onClienteClick: (String) -> Unit,
    onNovoClienteClick: () -> Unit,
    modifier: Modifier = Modifier,
    obterUltimaSessao: (String) -> String? = { null }
) {
    Scaffold(
        topBar = {
            MassoTopBar(titulo = "MassoSync — Clientes")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNovoClienteClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
                    .semantics {
                        contentDescription = "Cadastrar novo cliente"
                    }
            ) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (clientes.isEmpty()) {
            EmptyStateView(
                titulo = "Nenhum cliente cadastrado",
                mensagem = "Toque no botão '+' abaixo para cadastrar seu primeiro cliente e começar o acompanhamento.",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = innerPadding.calculateTopPadding() + 8.dp,
                    end = 16.dp,
                    bottom = innerPadding.calculateBottomPadding() + 80.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(clientes, key = { it.id }) { cliente ->
                    ClienteCard(
                        cliente = cliente,
                        ultimaSessao = obterUltimaSessao(cliente.id),
                        onClick = { onClienteClick(cliente.id) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ClientesScreenComDadosPreview() {
    val mockClientes = listOf(
        Cliente("1", "Maria Silva", "(11) 98765-4321"),
        Cliente("2", "João Souza", "(11) 91234-5678"),
        Cliente("3", "Ana Costa", "(21) 99999-0000")
    )
    MassoSyncTheme {
        ClientesScreen(
            clientes = mockClientes,
            onClienteClick = {},
            onNovoClienteClick = {},
            obterUltimaSessao = { id -> if (id == "1") "10/10/2026 - Massagem Relaxante" else null }
        )
    }
}

@Preview
@Composable
fun ClientesScreenVaziaPreview() {
    MassoSyncTheme {
        ClientesScreen(
            clientes = emptyList(),
            onClienteClick = {},
            onNovoClienteClick = {}
        )
    }
}
