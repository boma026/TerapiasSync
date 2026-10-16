package br.ufrn.imd.massosync

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.ufrn.imd.massosync.data.MassoRepository
import br.ufrn.imd.massosync.ui.screens.AdaptiveAppScreen
import br.ufrn.imd.massosync.ui.theme.MassoSyncTheme

@Composable
@Preview
fun App() {
    val repository = remember { MassoRepository() }

    MassoSyncTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
            color = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ) {
            AdaptiveAppScreen(repository = repository)
        }
    }
}
