package com.example.reciclerapi.recicler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reciclerapi.R
import com.example.reciclerapi.model.DogRespuesta

class MyAdapter(private val dataSet:DogRespuesta): RecyclerView.Adapter<MyView>() {
   lateinit var myContexto : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        myContexto = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyView(view)
    }

    override fun getItemCount() = dataSet.message!!.size

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val url: String = dataSet.message!![position]
        Glide.with(myContexto)
            .load(url)
            .into(holder.imV1)

    }

}