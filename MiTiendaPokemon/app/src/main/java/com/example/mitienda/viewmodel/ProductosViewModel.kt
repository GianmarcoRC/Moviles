package com.example.mitienda.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mitienda.model.Category
import com.example.mitienda.model.MainState
import com.example.mitienda.model.Product
import kotlinx.coroutines.launch

class ProductosViewModel : ViewModel() {
    private val mainState = MainState()

    private val _productosFiltrados = MutableLiveData<List<Product>>()
    val productosFiltrados: LiveData<List<Product>> = _productosFiltrados

    private val _categorias = MutableLiveData<List<Category>>()
    val categorias: LiveData<List<Category>> = _categorias

    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _paginaActual = MutableLiveData<Int>(1)
    val paginaActual: LiveData<Int> = _paginaActual

    private val _totalPaginas = MutableLiveData<Int>(1)
    val totalPaginas: LiveData<Int> = _totalPaginas

    private var categoriaSeleccionada: Long? = null
    private var terminoBusqueda: String? = null

    init {
        cargarCategorias()
        cargarProductos(1)
    }

    private fun cargarCategorias() {
        viewModelScope.launch {
            try {
                val categorias = mainState.getCategories()
                _categorias.value = categorias
            } catch (e: Exception) {
                _error.value = "Error al cargar categorías: ${e.message}"
            }
        }
    }

    fun cargarProductos(pagina: Int) {
        viewModelScope.launch {
            _cargando.value = true
            try {
                // Ajusta el número de página para que sea cero-based para la API
                val pageNumberForApi = if (pagina > 0) pagina - 1 else 0

                val paginaProductos = mainState.getProductsPaginated(
                    search = terminoBusqueda,
                    categoryId = categoriaSeleccionada,
                    pageNumber = pageNumberForApi,
                    pageSize = 5
                )

                _productosFiltrados.value = paginaProductos.content
                _paginaActual.value = paginaProductos.number + 1
                _totalPaginas.value = paginaProductos.totalPages
                _cargando.value = false
            } catch (e: Exception) {
                _error.value = "Error al cargar los productos: ${e.message}"
                _cargando.value = false
            }
        }
    }

    fun siguientePagina() {
        val actual = _paginaActual.value ?: 1
        val total = _totalPaginas.value ?: 1

        if (actual < total) {
            cargarProductos(actual + 1)
        }
    }

    fun anteriorPagina() {
        val actual = _paginaActual.value ?: 1

        if (actual > 1) {
            cargarProductos(actual - 1)
        }
    }

    fun filtrarPorCategoria(categoriaId: Long?) {
        categoriaSeleccionada = categoriaId
        _paginaActual.value = 1
        cargarProductos(1)
    }

    fun buscar(termino: String?) {
        terminoBusqueda = termino
        _paginaActual.value = 1
        cargarProductos(1)
    }
}

