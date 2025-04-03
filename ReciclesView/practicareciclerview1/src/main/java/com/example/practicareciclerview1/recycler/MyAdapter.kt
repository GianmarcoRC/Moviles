package com.example.practicareciclerview1.recycler;

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.practicareciclerview1.R


class MyAdapter(private val dataSet: Map<String, String>) : RecyclerView.Adapter<MyView>() {
    var clickPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyView(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val key = dataSet.keys.elementAt(position)
        val value = dataSet[key]
        val fondo = Color.parseColor(value)

        holder.tvColores.text = key
        holder.tvCodigo.text = value
        holder.fondo.setBackgroundColor(fondo)

        if (position == clickPosition) {
            holder.tvColores.setTextColor(fondo)
            holder.tvCodigo.setTextColor(fondo)
            holder.tvColores.setBackgroundColor(Color.WHITE)
            holder.tvCodigo.setBackgroundColor(Color.WHITE)
        } else {
            holder.tvColores.setTextColor(Color.WHITE)
            holder.tvCodigo.setTextColor(Color.WHITE)
            holder.tvColores.setBackgroundColor(fondo)
            holder.tvCodigo.setBackgroundColor(fondo)
        }

        holder.tvColores.setOnClickListener {
            notifyItemChanged(clickPosition)
            clickPosition = position
            notifyItemChanged(clickPosition)
        }

        holder.tvColores.setOnLongClickListener {
            if (clickPosition == position) {
                notifyItemChanged(clickPosition)
                clickPosition = -1
            }
            true
        }
    }

}
