package com.senac.teladenovaviagem.model

import java.util.Date

enum class TipoViagem{
    Lazer,
    Negocio
}

data class Viagem(
    val destino: String = "",
    val tipoViagem: TipoViagem = TipoViagem.Lazer,
    val dataInicial: Date = Date(0),
    val dataFinal: Date = Date(0),
    val orcamento: Float = 0.0f
){

}