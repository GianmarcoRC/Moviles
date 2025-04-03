package com.example.reciclesview.model

import android.icu.text.Transliterator.Position

class MainState {
    var animalNames = mutableListOf(
        "Caballo",
        "Vaca",
        "Perro",
        "Gato",
        "León",
        "Tigre",
        "Rinoceronte",
        "Elefante",
        "Mono",
        "Delfin",
        "Serpiente",
        "oso",
        "Cacatua",
        "tucan",
        "Mariposa"
    )

    fun devuelveArray(): List<String> {
        return animalNames;
    }
    fun delete(position:Int):MyData{
        animalNames.removeAt(position)
        return MyData(position, animalNames)
    }
    fun add(position:Int,animal:String):MyData{
        animalNames.add(position,animal)
        return MyData(position, animalNames)
    }
}