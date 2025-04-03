package com.example.reciclesview.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reciclesview.R

class MyView(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvAnimales: TextView = itemView.findViewById(R.id.tv1)
}