package com.example.mitienda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mitienda.databinding.FragmentProductsBinding
import com.example.mitienda.models.Category
import com.example.mitienda.models.MainState
import com.example.mitienda.models.TokenProviderImpl
import com.example.mitienda.recycler.CategoryAdapter
import com.example.mitienda.recycler.ProductAdapter
import com.example.mitienda.viewmodels.ProductsViewModel



class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductsViewModel
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val tokenProvider = TokenProviderImpl(requireContext())
        val mainState = MainState(tokenProvider)
        viewModel = ProductsViewModel(mainState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()

        binding.btnLoadMore.setOnClickListener {
            viewModel.nextPage()
        }
    }


    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(
            onProductClick = { producto ->
                val intent = Intent(requireContext(), ProductDetailActivity::class.java)
                intent.putExtra("PRODUCT_ID", producto.id.toLong())
                intent.putExtra("PRODUCT_NAME", producto.name)
                intent.putExtra("PRODUCT_PRICE", producto.price)
                intent.putExtra("PRODUCT_IMAGE", producto.image)
                intent.putExtra("NEEDS_TOKEN_PROVIDER", true)
                startActivity(intent)
            },
            categories = viewModel.categorias.value ?: emptyList()
        )

        binding.recyclerProductos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lm = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisible = lm.findLastVisibleItemPosition()
                    if (lastVisible == productAdapter.itemCount - 1) {
                        viewModel.nextPage()
                    }
                }
            })
        }

        Log.d("ProductsFragment", "Adapter assigned with ${productAdapter.itemCount} items")
    }

    private fun setupObservers() {
        viewModel.productosFiltrados.observe(viewLifecycleOwner) { productos ->
            productAdapter.setProductos(productos)
            binding.emptyView.visibility = if (productos.isEmpty()) View.VISIBLE else View.GONE

            val currentPage = viewModel.currentPage
            val totalPages = viewModel.totalPages
            binding.btnLoadMore.visibility = if (currentPage < totalPages) View.VISIBLE else View.GONE
        }


        viewModel.categorias.observe(viewLifecycleOwner) { categorias ->
            val todas = mutableListOf(Category(0, "All Categories")).apply { addAll(categorias) }
            val categoryAdapter = CategoryAdapter(requireContext(), android.R.layout.simple_spinner_item, todas)
            binding.spinnerCategoria.adapter = categoryAdapter
            binding.spinnerCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selected = todas[position]
                    if (selected.id == 0L) viewModel.showAllproducts()
                    else viewModel.filterbyCategory(selected.id)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        viewModel.cargando.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
