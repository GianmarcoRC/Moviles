package com.example.ciclodevida.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ciclodevida.Model.Datos
import com.example.ciclodevida.Model.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModelFlows : ViewModel() {
    private val _datos = MutableStateFlow(Datos(0,0,false))
    //EN VES de livedata es stateflows
    val datos: StateFlow<Datos>
        get() = _datos
    val myEstado = MainState()

    fun sumar(valor:Int,misDatos: Datos){
        viewModelScope.launch {
            var retornoDatos = myEstado.sumar(valor,misDatos)
            _datos.value = retornoDatos
        }
    }
}