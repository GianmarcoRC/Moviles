package com.example.ciclodevida.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ciclodevida.Model.Datos
import com.example.ciclodevida.Model.MainState
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    private val _datos = MutableLiveData(Datos(0,0,false))
    val datos: LiveData<Datos>get() = _datos
    val myEstado = MainState()

    fun sumar(valor:Int,misDatos: Datos){
        viewModelScope.launch {
            var retornoDatos = myEstado.sumar(valor,misDatos)
            _datos.value = retornoDatos
        }
    }
}