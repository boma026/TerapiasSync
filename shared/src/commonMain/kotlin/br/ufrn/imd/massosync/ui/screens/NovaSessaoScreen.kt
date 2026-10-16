package br.ufrn.imd.massosync.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.ufrn.imd.massosync.model.Cliente
import br.ufrn.imd.massosync.model.Tecnica
import br.ufrn.imd.massosync.ui.components.MassoTopBar
import br.ufrn.imd.massosync.ui.components.TecnicaSelector
import br.ufrn.imd.massosync.ui.theme.MassoSyncTheme

@Composable
fun NovaSessaoScreen(
    cliente: Cliente?,
    onSalvar: (tecnica: String, data: String, observacoes: String) -> Unit,
    modifier: Modifier = Modifier,
    onVoltar: (() -> Unit)? = null
) {
    var tecnicaSelecionada by remember { mutableStateOf(Tecnica.MASSAGEM_RELAXANTE.titulo) }
    var data by remember { mutableStateOf("16/10/2026") }
    var observacoes by remember { mutableStateOf("") }
    var dataModificada by remember { mutableStateOf(false) }

    val dataValida = data.isNotBlank() && data.trim().length >= 8
    val formularioValido = dataValida && tecnicaSelecionada.isNotBlank()

    Scaffold(
        topBar = {
            MassoTopBar(
                titulo = "Nova Sessão",
                onVoltar = onVoltar
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Cliente: ${cliente?.nome ?: "Não identificado"}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.semantics { heading() }
            )
            Spacer(modifier = Modifier.height(16.dp))

            TecnicaSelector(
                tecnicaSelecionada = tecnicaSelecionada,
                onTecnicaSelecionada = { tecnicaSelecionada = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = data,
                onValueChange = {
                    data = it
                    dataModificada = true
                },
                label = { Text("Data do Atendimento (DD/MM/AAAA) *") },
                placeholder = { Text("Ex: 16/10/2026") },
                isError = dataModificada && !dataValida,
                supportingText = {
                    if (dataModificada && !dataValida) {
                        Text(
                            text = "A data deve conter dia, mês e ano",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 48.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = observacoes,
                onValueChange = { observacoes = it },
                label = { Text("Evolução / Observações Clínicas") },
                placeholder = { Text("Ex: Queixa de dor na coluna; boa resposta à massagem.") },
                minLines = 3,
                maxLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 48.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (formularioValido) {
                        onSalvar(tecnicaSelecionada, data.trim(), observacoes.trim())
                    }
                },
                enabled = formularioValido,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 48.dp)
            ) {
                Text(
                    text = "Registrar Atendimento",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun NovaSessaoScreenPreview() {
    val mockCliente = Cliente("1", "Maria Silva", "(11) 98765-4321")
    MassoSyncTheme {
        NovaSessaoScreen(
            cliente = mockCliente,
            onSalvar = { _, _, _ -> },
            onVoltar = {}
        )
    }
}
