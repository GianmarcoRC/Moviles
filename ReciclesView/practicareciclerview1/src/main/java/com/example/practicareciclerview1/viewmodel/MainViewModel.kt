package com.example.practicareciclerview1.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicareciclerview1.model.MyData
import com.example.practicareciclerview1.model.MainState
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    private val _datos = MutableLiveData<Map<String,String>>(emptyMap())
    val datos: MutableLiveData<Map<String,String>> get() = _datos
    val myEstado = MainState()
    private val _delete : MutableLiveData<MyData> = MutableLiveData<MyData>()
    val delete: MutableLiveData<MyData> get() = _delete
    private val _add : MutableLiveData<MyData> = MutableLiveData<MyData>()
    val add: MutableLiveData<MyData> get() = _add

    fun devuelveArray(){
        viewModelScope.launch{
            var retornoDatos = myEstado.devuelveArray()
            _datos.value = retornoDatos
        }
    }
    fun delete(position: Int){
        viewModelScope.launch{
            var retornoDatos = myEstado.delete(position)
            _delete.value = retornoDatos
        }
    }
    fun add(position: Int,hexadecimal:String, color:String){
        viewModelScope.launch{
            var retornoDatos = myEstado.add(position,hexadecimal, color)
            _add.value = retornoDatos
        }
    }
}
