package com.example.mitienda.viewmodels



import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mitienda.models.MainState
import com.example.mitienda.models.ShoppingCartDto
import kotlinx.coroutines.launch

class CartViewModel(private val mainState: MainState) : ViewModel() {

    private val _cartItems = MutableLiveData<List<ShoppingCartDto>>()
    val cartItems: LiveData<List<ShoppingCartDto>> = _cartItems

    private val _totalAmount = MutableLiveData<Double>()
    val totalAmount: LiveData<Double> = _totalAmount

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        loadCart()
    }

    fun loadCart() {
        _loading.value = true
        Log.d("CartViewModel", "Iniciando carga del carrito...")
        viewModelScope.launch {
            try {
                val cartDto = mainState.getCart()
                Log.d("CartViewModel", "Respuesta del servidor (CartDto): $cartDto")
                if (cartDto != null) {
                    val items = cartDto.shoppingCartProducts ?: emptyList()
                    _cartItems.value = items

                    val total = items.sumOf {
                        it.totalPrice ?: ((it.price ?: 0.0) * (it.quantity ?: 0))
                    }
                    _totalAmount.value = total

                    Log.d("CartViewModel", "Carrito cargado. Items: ${items.size}, Total: $total")
                } else {
                    _cartItems.value = emptyList()
                    _totalAmount.value = 0.0
                    _error.value = "Error al cargar el carrito: Respuesta nula"
                    Log.e("CartViewModel", "Error al cargar el carrito: Respuesta nula")
                }
                _loading.value = false
            } catch (e: Exception) {
                _error.value = "Error al cargar el carrito: ${e.message}"
                _loading.value = false
                Log.e("CartViewModel", "Excepción al cargar el carrito", e)
            }
        }
    }

}