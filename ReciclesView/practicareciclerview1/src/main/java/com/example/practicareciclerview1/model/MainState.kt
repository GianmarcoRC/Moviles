package com.example.practicareciclerview1.model

class MainState {
    var coloresName =
        mutableMapOf("Pizarra" to "#708090", "Negro" to "#000000"
        ,"Rosa" to "#FF69B4", "Rojo" to "#FF0000","Naranja" to "#FFA500","Marron" to "#A52A2A")

    fun devuelveArray(): Map<String, String> {
        return coloresName;
    }
    fun delete(position:Int):MyData{
        val key = coloresName.keys.elementAtOrNull(position)

        if (key != null) {
            coloresName.remove(key)
        }
        return MyData(position, coloresName)
    }
    fun add(position:Int,hexadecimal:String,color:String):MyData{
        coloresName[color] = hexadecimal
        return MyData(position, coloresName)
    }
}