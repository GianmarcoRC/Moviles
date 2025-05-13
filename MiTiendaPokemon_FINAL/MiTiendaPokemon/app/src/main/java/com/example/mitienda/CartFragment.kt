package com.example.mitienda

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mitienda.databinding.FragmentCartBinding
import com.example.mitienda.models.MainState
import com.example.mitienda.models.ShoppingCartDto
import com.example.mitienda.models.TokenProviderImpl
import com.example.mitienda.recycler.CartAdapter
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import com.example.mitienda.viewmodels.CartViewModel

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val tokenProvider = TokenProviderImpl(requireContext())
        val mainState = MainState(tokenProvider)
        cartViewModel = CartViewModel(mainState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        cartViewModel.loadCart()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(emptyList()) { item ->
            showDeleteDialog(item)
        }
        binding.recyclerViewCarrito.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun showDeleteDialog(item: ShoppingCartDto) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete product")
            .setMessage("Do you want to remove '${item.name}' from the cart?")
            .setPositiveButton("Yes") { dialog, _ ->
                deleteFromCart(item.id)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun deleteFromCart(itemId: Long?) {
        itemId?.let { id ->
            val tokenProvider = TokenProviderImpl(requireContext())
            val mainState = MainState(tokenProvider)
            lifecycleScope.launch {
                try {
                    val cartDto = mainState.deleteFromCart(id)
                    if (cartDto != null) {
                        cartViewModel.loadCart()
                        Toast.makeText(requireContext(), "Deleted successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Error deleting product", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun observeViewModel() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.updateData(items)
            binding.textViewCarritoVacio.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
            binding.textViewTotalCarrito.visibility = if (items.isEmpty()) View.GONE else View.VISIBLE
            binding.recyclerViewCarrito.visibility = if (items.isEmpty()) View.GONE else View.VISIBLE
        }

        cartViewModel.totalAmount.observe(viewLifecycleOwner) { total ->
            binding.textViewTotalCarrito.text = "Total: ${String.format("%.2f", total ?: 0.0)}€"
        }

        cartViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarCarrito.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        cartViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
