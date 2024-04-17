package com.senac.teladenovaviagem

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.senac.teladenovaviagem.components.LabelComponent
import com.senac.teladenovaviagem.components.MyTopBar
import com.senac.teladenovaviagem.model.TipoViagem
import com.senac.teladenovaviagem.ui.theme.TelaDeNovaViagemTheme
import com.senac.teladenovaviagem.viewmodel.ViagemViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            MyTopBar()
        }
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            Content(snackbarHostState)
        }
    }
}


@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(snackbarHostState: SnackbarHostState, viagemViewModel: ViagemViewModel = viewModel()){
    val viagemState = viagemViewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope()
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

    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        LabelComponent(labelResource = R.string.dataInicial)
    }

    val showDatePickerDialog = remember {
        mutableStateOf(false)
    }
    val selectedDate = remember {
        mutableStateOf("")
    }
    val datePickerState = rememberDatePickerState()

    Row {
        if (showDatePickerDialog.value) {
            DatePickerDialog(
                onDismissRequest = { showDatePickerDialog.value = false },
                confirmButton = {
                    Button(
                        onClick = {
                            datePickerState
                                .selectedDateMillis?.let { millis ->
                                    selectedDate.value = millis.toBrazilianDateFormat()
                                }
                            showDatePickerDialog.value = false
                        }) {
                        Text(text = "Escolher data")
                    }
                }) {
                DatePicker(state = datePickerState)
            }
        }
        TextField(
            value = selectedDate.value,
            onValueChange = { SimpleDateFormat("dd/MM/yyyy").parse(it)
                ?.let { it1 -> viagemViewModel.updateDataInicial(it1) } },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) {
                        showDatePickerDialog.value = true
                    }
                },
            readOnly = true
        )
    }

    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        LabelComponent(labelResource = R.string.dataFinal)
    }

    val showDatePickerDialogFinal = remember {
        mutableStateOf(false)
    }
    val selectedDateFinal = remember {
        mutableStateOf("")
    }
    val datePickerStateFinal = rememberDatePickerState()

    Row {
        if (showDatePickerDialogFinal.value) {
            DatePickerDialog(
                onDismissRequest = { showDatePickerDialogFinal.value = false },
                confirmButton = {
                    Button(
                        onClick = {
                            datePickerStateFinal
                                .selectedDateMillis?.let { millis ->
                                    selectedDateFinal.value = millis.toBrazilianDateFormat()
                                }
                            showDatePickerDialogFinal.value = false
                        }) {
                        Text(text = "Escolher data")
                    }
                }) {
                DatePicker(state = datePickerStateFinal)
            }
        }
        TextField(
            value = selectedDateFinal.value,
            onValueChange = { SimpleDateFormat("dd/MM/yyyy").parse(it)
                ?.let { it1 -> viagemViewModel.updateDataFinal(it1) } },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) {
                        showDatePickerDialogFinal.value = true
                    }
                },
            readOnly = true
        )
    }

    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        LabelComponent(labelResource = R.string.orcamento)
    }
    Row {
        TextField(
            value = viagemState.value.orcamento.toString(),
            onValueChange = { viagemViewModel.updateOrcamento(it.toFloat()) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.weight(4f)
                .padding(top = 10.dp)
        )
    }

    Row {
        Button(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Viagem registrada com sucesso!")
                }
            },
            modifier = Modifier.weight(4f)
                .padding(top = 10.dp)
                .align(Alignment.CenterVertically)

        )
        {Text("Salvar")}
    }
}

fun Long.toBrazilianDateFormat(
    pattern: String = "dd/MM/yyyy"
): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(
        pattern, Locale("pt-br")
    ).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    return formatter.format(date)
}