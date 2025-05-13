package com.example.mitienda

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.mitienda.databinding.ActivityProductDetailBinding
import com.example.mitienda.models.MainState
import com.example.mitienda.models.TokenProviderImpl
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var mainState: MainState

    private var quantity = 1
    private var productId: Long = -1
    private var productName: String = ""
    private var productPrice: Double = 0.0
    private var productImage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val needsTokenProvider = intent.getBooleanExtra("NEEDS_TOKEN_PROVIDER", false)
        if (needsTokenProvider) {
            val tokenProvider = TokenProviderImpl(this)
            mainState = MainState(tokenProvider)
        } else {
            mainState = MainState(TokenProviderImpl(this))
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detalles del Producto"

        // Obtener los datos del intent
        productId = intent.getLongExtra("PRODUCT_ID", -1)
        productName = intent.getStringExtra("PRODUCT_NAME") ?: ""
        productPrice = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)
        productImage = intent.getStringExtra("PRODUCT_IMAGE") ?: ""

        setupProductDetails()
        setupQuantityButtons()
        setupAddToCartButton()
    }

    private fun setupProductDetails() {
        binding.textViewProductNameDetail.text = productName
        binding.textViewPriceDetail.text = "Precio: $productPrice€"

        // Construir la URL completa de la imagen
        val fullImageUrl = "http://10.0.2.2:8000$productImage"
        Glide.with(this)
            .load(fullImageUrl)
            // .placeholder(R.drawable.placeholder_image)
            // .error(R.drawable.error_image)
            .into(binding.imageViewProductDetail)
    }

    private fun setupQuantityButtons() {
        binding.textViewQuantity.text = quantity.toString()

        binding.buttonDecrease.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.textViewQuantity.text = quantity.toString()
            }
        }

        binding.buttonIncrease.setOnClickListener {
            quantity++
            binding.textViewQuantity.text = quantity.toString()
        }
    }

    private fun setupAddToCartButton() {
        binding.buttonAddToCart.setOnClickListener {
            if (productId != -1L) {
                addToCart(productId, quantity)
            }
        }
    }

    private fun addToCart(productId: Long, quantity: Int) {
        binding.buttonAddToCart.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = mainState.addToCart(productId, quantity)
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(
                        this@ProductDetailActivity,
                        "$quantity unidad(es) de $productName añadidas al carrito",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {

                    when (response.code()) {
                        409 -> {
                            Toast.makeText(
                                this@ProductDetailActivity,
                                "No hay suficiente stock disponible de $productName",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                this@ProductDetailActivity,
                                "Error al añadir al carrito",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ProductDetailActivity,
                    "Error de conexión: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                binding.buttonAddToCart.isEnabled = true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}