package com.example.ciclodevida.Model

class MainState {
    fun sumar(valor : Int,datos : Datos): Datos{
        datos.contator +=valor
        datos.numClicks++
        if (datos.numClicks==5){
            datos.mostrarmensaje = true
            datos.numClicks=0
        }
        return datos
    }
}