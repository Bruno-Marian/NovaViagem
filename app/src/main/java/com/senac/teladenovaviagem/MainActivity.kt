package com.senac.teladenovaviagem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.senac.teladenovaviagem.components.LabelComponent
import com.senac.teladenovaviagem.components.MyTopBar
import com.senac.teladenovaviagem.model.TipoViagem
import com.senac.teladenovaviagem.ui.theme.TelaDeNovaViagemTheme
import com.senac.teladenovaviagem.viewmodel.ViagemViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaDeNovaViagemTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Myapp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Myapp(){
    Scaffold (
        topBar = {
            MyTopBar()
        }
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            Content()
        }
    }
}


@Composable
private fun Content(viagemViewModel: ViagemViewModel = viewModel()){
    val viagemState = viagemViewModel.uiState.collectAsState()

    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        LabelComponent(labelResource = R.string.destino)
    }

    Row {
        TextField(
            value = viagemState.value.destino,
            onValueChange = { viagemViewModel.updateDestino(it) },
            modifier = Modifier.weight(4f)
                .padding(top = 10.dp)
        )
    }

    Row {
        LabelComponent(labelResource = R.string.tipoViagem)
    }

    Row (
        verticalAlignment = Alignment.CenterVertically
    ){

        RadioButton(
            selected = viagemState.value.tipoViagem == TipoViagem.Lazer,
            onClick = { viagemViewModel.updateTipoViagem(TipoViagem.Lazer) },
            modifier = Modifier
                .weight(0.5f)
        )
        LabelComponent(labelResource = R.string.lazer)

        RadioButton(
            selected = viagemState.value.tipoViagem == TipoViagem.Negocio,
            onClick = { viagemViewModel.updateTipoViagem(TipoViagem.Negocio) },
            modifier = Modifier
                .weight(0.5f)
        )
        LabelComponent(labelResource = R.string.negocios)
    }
}