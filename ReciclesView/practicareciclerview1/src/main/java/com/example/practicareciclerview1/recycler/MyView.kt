package com.example.practicareciclerview1.recycler

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicareciclerview1.R

class MyView(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvColores: TextView = itemView.findViewById(R.id.txtTextoColor)
    val tvCodigo: TextView = itemView.findViewById(R.id.txtCodHexa)
    val fondo : LinearLayout = itemView.findViewById(R.id.fondo)
}