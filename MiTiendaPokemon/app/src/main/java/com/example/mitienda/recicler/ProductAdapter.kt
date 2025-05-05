package com.example.mitienda.recicler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mitienda.R
import com.example.mitienda.model.Product

class ProductAdapter(private val onProductoClick: (Product) -> Unit) : RecyclerView.Adapter<ProductView>() {

    private var productos: List<Product> = emptyList()
    private var categoriasVisibles = mutableSetOf<Long>()

    fun setProductos(nuevosProductos: List<Product>) {
        productos = nuevosProductos
        categoriasVisibles.clear()
        productos.forEach { producto ->
            categoriasVisibles.add(producto.categoryId)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductView(view, onProductoClick)
    }

    override fun onBindViewHolder(holder: ProductView, position: Int) {
        val producto = productos[position]

        val mostrarEncabezadoCategoria = position == 0 ||
                producto.categoryId != productos[position - 1].categoryId

        holder.bind(producto, mostrarEncabezadoCategoria)
    }

    override fun getItemCount(): Int = productos.size
}