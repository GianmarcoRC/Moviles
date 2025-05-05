package com.example.mitienda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
    private lateinit var productAdapter: ProductAdapter

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
    }

    private fun configurarRecyclerView() {
        productAdapter = ProductAdapter { producto ->
            val intent = Intent(requireContext(), ProductDetailActivity::class.java)
            intent.putExtra("PRODUCT_ID", producto.id)
            intent.putExtra("PRODUCT_NAME", producto.name)
            intent.putExtra("PRODUCT_PRICE", producto.price)
            intent.putExtra("PRODUCT_IMAGE", producto.image)
            startActivity(intent)
        }

        binding.recyclerProductos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }

    private fun configurarObservadores() {
        viewModel.productosFiltrados.observe(viewLifecycleOwner) { productos ->
            productAdapter.setProductos(productos)
            binding.emptyView.visibility = if (productos.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.categorias.observe(viewLifecycleOwner) { categorias ->
            val adapter = CategoryAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categorias
            )
            binding.spinnerCategoria.adapter = adapter

            binding.spinnerCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val categoriaSeleccionada = categorias[position]
                    viewModel.filtrarPorCategoria(categoriaSeleccionada.id)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // No hacer nada
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}