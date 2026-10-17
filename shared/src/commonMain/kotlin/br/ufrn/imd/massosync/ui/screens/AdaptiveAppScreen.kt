package br.ufrn.imd.massosync.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import androidx.window.core.layout.WindowWidthSizeClass
import br.ufrn.imd.massosync.data.MassoRepository
import br.ufrn.imd.massosync.navigation.DeepLinkConfig
import br.ufrn.imd.massosync.navigation.Routes
import br.ufrn.imd.massosync.ui.components.EmptyStateView

@Composable
fun AdaptiveAppScreen(
    repository: MassoRepository,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val isLargo = adaptiveInfo.windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT

    if (isLargo) {
        LayoutLargo(
            repository = repository,
            modifier = modifier
        )
    } else {
        LayoutCompacto(
            repository = repository,
            navController = navController,
            modifier = modifier
        )
    }
}

@Composable
private fun LayoutCompacto(
    repository: MassoRepository,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Clientes,
        modifier = modifier.fillMaxSize()
    ) {
        composable<Routes.Clientes> {
            val clientes = repository.listarClientes()
            ClientesScreen(
                clientes = clientes,
                onClienteClick = { id ->
                    navController.navigate(Routes.DetalheCliente(clienteId = id))
                },
                onNovoClienteClick = {
                    navController.navigate(Routes.CadastroCliente)
                },
                obterUltimaSessao = { id ->
                    repository.buscarUltimaSessao(id)?.let { "${it.data} - ${it.tecnica}" }
                }
            )
        }

        composable<Routes.CadastroCliente> {
            CadastroClienteScreen(
                onSalvar = { nome, telefone ->
                    repository.adicionarCliente(nome, telefone)
                    navController.popBackStack()
                },
                onVoltar = {
                    navController.popBackStack()
                }
            )
        }

        composable<Routes.DetalheCliente>(
            deepLinks = listOf(
                navDeepLink<Routes.DetalheCliente>(basePath = DeepLinkConfig.CLIENTE_BASE_URL)
            )
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<Routes.DetalheCliente>()
            val cliente = repository.buscarClientePorId(route.clienteId)
            val sessoes = repository.listarSessoesPorCliente(route.clienteId)

            DetalheClienteScreen(
                cliente = cliente,
                sessoes = sessoes,
                onNovaSessaoClick = { id ->
                    navController.navigate(Routes.NovaSessao(clienteId = id))
                },
                onVoltar = {
                    navController.popBackStack()
                }
            )
        }

        composable<Routes.NovaSessao> { backStackEntry ->
            val route = backStackEntry.toRoute<Routes.NovaSessao>()
            val cliente = repository.buscarClientePorId(route.clienteId)

            NovaSessaoScreen(
                cliente = cliente,
                onSalvar = { tecnica, data, obs ->
                    repository.adicionarSessao(route.clienteId, tecnica, data, obs)
                    navController.popBackStack()
                },
                onVoltar = {
                    navController.popBackStack()
                }
            )
        }
    }
}

private sealed interface PainelDireito {
    data object Nenhum : PainelDireito
    data object NovoCliente : PainelDireito
    data class NovaSessao(val clienteId: String) : PainelDireito
}

@Composable
private fun LayoutLargo(
    repository: MassoRepository,
    modifier: Modifier = Modifier
) {
    val clientes = repository.listarClientes()
    var clienteSelecionadoId by remember {
        mutableStateOf(clientes.firstOrNull()?.id)
    }
    var painelDireito by remember {
        mutableStateOf<PainelDireito>(PainelDireito.Nenhum)
    }

    Row(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .width(360.dp)
                .fillMaxHeight()
        ) {
            ClientesScreen(
                clientes = clientes,
                onClienteClick = { id ->
                    clienteSelecionadoId = id
                    painelDireito = PainelDireito.Nenhum
                },
                onNovoClienteClick = {
                    painelDireito = PainelDireito.NovoCliente
                },
                obterUltimaSessao = { id ->
                    repository.buscarUltimaSessao(id)?.let { "${it.data} - ${it.tecnica}" }
                }
            )
        }

        VerticalDivider()

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            when (val painel = painelDireito) {
                is PainelDireito.NovoCliente -> {
                    CadastroClienteScreen(
                        onSalvar = { nome, telefone ->
                            val novo = repository.adicionarCliente(nome, telefone)
                            clienteSelecionadoId = novo.id
                            painelDireito = PainelDireito.Nenhum
                        },
                        onVoltar = {
                            painelDireito = PainelDireito.Nenhum
                        }
                    )
                }
                is PainelDireito.NovaSessao -> {
                    val cliente = repository.buscarClientePorId(painel.clienteId)
                    NovaSessaoScreen(
                        cliente = cliente,
                        onSalvar = { tecnica, data, obs ->
                            repository.adicionarSessao(painel.clienteId, tecnica, data, obs)
                            painelDireito = PainelDireito.Nenhum
                        },
                        onVoltar = {
                            painelDireito = PainelDireito.Nenhum
                        }
                    )
                }
                PainelDireito.Nenhum -> {
                    val idAtual = clienteSelecionadoId
                    if (idAtual != null) {
                        val cliente = repository.buscarClientePorId(idAtual)
                        val sessoes = repository.listarSessoesPorCliente(idAtual)
                        DetalheClienteScreen(
                            cliente = cliente,
                            sessoes = sessoes,
                            onNovaSessaoClick = { id ->
                                painelDireito = PainelDireito.NovaSessao(id)
                            },
                            onVoltar = null
                        )
                    } else {
                        EmptyStateView(
                            titulo = "Nenhum cliente selecionado",
                            mensagem = "Selecione um cliente na lista lateral para " +
                                "ver seus dados e histórico de sessões.",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp)
                        )
                    }
                }
            }
        }
    }
}
