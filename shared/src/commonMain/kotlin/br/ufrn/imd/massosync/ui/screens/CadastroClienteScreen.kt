package br.ufrn.imd.massosync.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.ufrn.imd.massosync.ui.components.MassoTopBar
import br.ufrn.imd.massosync.ui.theme.MassoSyncTheme

@Composable
fun CadastroClienteScreen(
    onSalvar: (nome: String, telefone: String) -> Unit,
    modifier: Modifier = Modifier,
    onVoltar: (() -> Unit)? = null
) {
    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var nomeModificado by remember { mutableStateOf(false) }
    var telefoneModificado by remember { mutableStateOf(false) }

    val nomeValido = nome.isNotBlank()
    val telefoneValido = telefone.trim().length >= 8
    val formularioValido = nomeValido && telefoneValido

    Scaffold(
        topBar = {
            MassoTopBar(
                titulo = "Novo Cliente",
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
                text = "Informações de Contato",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.semantics { heading() }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Preencha o nome e o telefone do cliente para cadastrá-lo.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = nome,
                onValueChange = {
                    nome = it
                    nomeModificado = true
                },
                label = { Text("Nome completo *") },
                placeholder = { Text("Ex: Maria Silva") },
                isError = nomeModificado && !nomeValido,
                supportingText = {
                    if (nomeModificado && !nomeValido) {
                        Text(
                            text = "O nome é obrigatório",
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
                value = telefone,
                onValueChange = {
                    telefone = it
                    telefoneModificado = true
                },
                label = { Text("Telefone / WhatsApp *") },
                placeholder = { Text("Ex: (11) 98765-4321") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = telefoneModificado && !telefoneValido,
                supportingText = {
                    if (telefoneModificado && !telefoneValido) {
                        Text(
                            text = "Informe um telefone válido (mínimo 8 dígitos)",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 48.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (formularioValido) {
                        onSalvar(nome.trim(), telefone.trim())
                    }
                },
                enabled = formularioValido,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 48.dp)
            ) {
                Text(
                    text = "Cadastrar Cliente",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun CadastroClienteScreenPreview() {
    MassoSyncTheme {
        CadastroClienteScreen(
            onSalvar = { _, _ -> },
            onVoltar = {}
        )
    }
}
