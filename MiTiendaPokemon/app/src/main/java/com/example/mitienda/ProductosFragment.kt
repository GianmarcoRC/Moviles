package com.example.mitienda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mitienda.databinding.FragmentProductosBinding
import com.example.mitienda.recicler.CategoryAdapter
import com.example.mitienda.recicler.ProductAdapter
import com.example.mitienda.viewmodel.ProductosViewModel

class ProductosFragment : Fragment() {
    private var _binding: FragmentProductosBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductosViewModel
    private lateinit var adaptadorProducto: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductosViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarRecyclerView()
        configurarObservadores()
        configurarPaginacion()
/*
        configurarBuscador()
*/
    }

    private fun configurarRecyclerView() {
        adaptadorProducto = ProductAdapter { producto ->
            val intent = Intent(requireContext(), ProductDetailActivity::class.java)
            intent.putExtra("PRODUCT_ID", producto.id.toLong())
            intent.putExtra("PRODUCT_NAME", producto.name)
            intent.putExtra("PRODUCT_PRICE", producto.price)
            intent.putExtra("PRODUCT_IMAGE", producto.image)
            startActivity(intent)
        }

        binding.recyclerProductos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adaptadorProducto
        }
    }

    private fun configurarObservadores() {
        viewModel.productosFiltrados.observe(viewLifecycleOwner) { productos ->
            adaptadorProducto.setProductos(productos)
            binding.emptyView.visibility = if (productos.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.categorias.observe(viewLifecycleOwner) { categorias ->
            val adaptador = CategoryAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categorias
            )
            binding.spinnerCategoria.adapter = adaptador

            binding.spinnerCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val categoriaSeleccionada = categorias[position]
                    viewModel.filtrarPorCategoria(categoriaSeleccionada.id)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }

        viewModel.cargando.observe(viewLifecycleOwner) { estaCargando ->
            binding.progressBar.visibility = if (estaCargando) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { mensajeError ->
            Toast.makeText(requireContext(), mensajeError, Toast.LENGTH_LONG).show()
        }

        viewModel.paginaActual.observe(viewLifecycleOwner) { pagina ->
            binding.textViewPagina.text = "Página $pagina de ${viewModel.totalPaginas.value ?: 1}"
            binding.buttonAnterior.isEnabled = pagina > 1
            binding.buttonSiguiente.isEnabled = pagina < (viewModel.totalPaginas.value ?: 1)
        }
    }

    private fun configurarPaginacion() {
        binding.buttonAnterior.setOnClickListener {
            viewModel.anteriorPagina()
        }

        binding.buttonSiguiente.setOnClickListener {
            viewModel.siguientePagina()
        }
    }

    /*private fun configurarBuscador() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.buscar(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.buscar(null)
                }
                return true
            }
        })
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}