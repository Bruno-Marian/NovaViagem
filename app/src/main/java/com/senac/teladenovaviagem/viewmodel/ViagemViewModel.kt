package com.senac.teladenovaviagem.viewmodel

import androidx.lifecycle.ViewModel
import com.senac.teladenovaviagem.model.TipoViagem
import com.senac.teladenovaviagem.model.Viagem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

class ViagemViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(Viagem())
    val uiState: StateFlow<Viagem> = _uiState.asStateFlow()

    fun updateDestino(newDestino: String) {
        _uiState.update {
            it.copy(destino = newDestino)
        }
    }

    fun updateTipoViagem(newTipoViagem: TipoViagem) {
        _uiState.update {
            it.copy(tipoViagem = newTipoViagem)
        }
    }

    fun updateDataInicial(newDataInicial: Date){
        _uiState.update {
            it.copy(dataInicial = newDataInicial)
        }
    }

    fun updateDataFinal(newDataFinal: Date){
        _uiState.update {
            it.copy(dataFinal = newDataFinal)
        }
    }

    fun updateOrcamento(newOrcamento: Float){
        _uiState.update {
            it.copy(orcamento = newOrcamento)
        }
    }
}