package com.example.mitienda.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mitienda.models.Category
import com.example.mitienda.models.MainState
import com.example.mitienda.models.Product
import kotlinx.coroutines.launch

class ProductsViewModel(private val mainState: MainState) : ViewModel() {

    private val _productosFiltrados = MutableLiveData<List<Product>>(emptyList())
    val productosFiltrados: LiveData<List<Product>> = _productosFiltrados

    private val _categorias = MutableLiveData<List<Category>>()
    val categorias: LiveData<List<Category>> = _categorias

    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    var currentPage = 1
    var totalPages = 1
    private val pageSize = 5

    private var categoriaSeleccionada: Long? = null
    private var terminoBusqueda: String? = null
    private val allProducts = mutableListOf<Product>()

    init {
        loadCategories()
        loadProducts()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categorias = mainState.getCategories()
                _categorias.value = categorias
            } catch (e: Exception) {
                _error.value = "Error loading categories: ${e.message}"
            }
        }
    }

    private fun loadProducts(reset: Boolean = false) {
        viewModelScope.launch {
            _cargando.value = true
            try {
                val page = mainState.getProductsPaginated(
                    search = terminoBusqueda,
                    categoryId = categoriaSeleccionada,
                    pageNumber = currentPage,
                    pageSize = pageSize
                )

                totalPages = page.totalPages

                if (reset) {
                    allProducts.clear()
                }

                allProducts.addAll(page.content)
                _productosFiltrados.value = allProducts.toList()

                _cargando.value = false
            } catch (e: Exception) {
                _error.value = "Error loading products: ${e.message}"
                _cargando.value = false
            }
        }
    }

    fun filterbyCategory(categoriaId: Long?) {
        categoriaSeleccionada = categoriaId
        currentPage = 1
        loadProducts(reset = true)
    }

    fun find(termino: String?) {
        terminoBusqueda = termino
        currentPage = 1
        loadProducts(reset = true)
    }

    fun nextPage() {
        if (currentPage < totalPages && cargando.value != true) {
            currentPage++
            loadProducts()
        }
    }

    fun showAllproducts() {
        categoriaSeleccionada = null
        currentPage = 1
        loadProducts(reset = true)
    }
}
