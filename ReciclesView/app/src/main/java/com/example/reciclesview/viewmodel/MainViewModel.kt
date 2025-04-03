package com.example.reciclesview.viewmodel

import android.icu.text.Transliterator.Position
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.reciclesview.model.MainState
import com.example.reciclesview.model.MyData
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    private val _datos = MutableLiveData<List<String>>(emptyList())
    val datos: LiveData<List<String>> get() = _datos
    val myEstado = MainState()
    private val _delete : MutableLiveData<MyData> = MutableLiveData<MyData>()
    val delete: LiveData<MyData> get() = _delete
    private val _add : MutableLiveData<MyData> = MutableLiveData<MyData>()
    val add: LiveData<MyData> get() = _add
    fun devuelveArray(){
        viewModelScope.launch{
            var retornoDatos = myEstado.devuelveArray()
            _datos.value = retornoDatos
        }
    }
    fun delete(position: Int){
        viewModelScope.launch {
            _delete.value = myEstado.delete(position)
        }

    }
    fun add(position: Int,name: String){
        viewModelScope.launch {
            var retornoDatos = myEstado.add(position,name)
            _add.value = retornoDatos
        }
    }
}