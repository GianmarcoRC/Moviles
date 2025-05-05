package com.example.mitienda.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mitienda.model.CartItemDto

import com.example.mitienda.model.MainState
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val estadoPrincipal = MainState()

    private val _itemsCarrito = MutableLiveData<List<CartItemDto>>()
    val itemsCarrito: LiveData<List<CartItemDto>> = _itemsCarrito

    private val _totalCarrito = MutableLiveData<Double>()
    val totalCarrito: LiveData<Double> = _totalCarrito

    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        cargarCarrito()
    }

    fun cargarCarrito() {
        viewModelScope.launch {
            try {
                _cargando.value = true
                val carrito = estadoPrincipal.obtenerCarrito()
                _itemsCarrito.value = carrito.size
                _totalCarrito.value = carrito.totalAmount
                _cargando.value = false
            } catch (e: Exception) {
                _error.value = "Error al cargar el carrito: ${e.message}"
                _cargando.value = false
            }
        }
    }

    fun eliminarDelCarrito(idItem: Long) {
        viewModelScope.launch {
            try {
                _cargando.value = true
                val carrito = estadoPrincipal.eliminarDelCarrito(idItem)
                _itemsCarrito.value = carrito.items
                _totalCarrito.value = carrito.totalAmount
                _cargando.value = false
            } catch (e: Exception) {
                _error.value = "Error al eliminar del carrito: ${e.message}"
                _cargando.value = false
            }
        }
    }
}