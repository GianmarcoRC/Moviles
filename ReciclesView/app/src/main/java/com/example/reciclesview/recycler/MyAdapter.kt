package com.example.reciclesview.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reciclesview.R

class MyAdapter(private val dataSet : List<String>) : RecyclerView.Adapter<MyView>() {
    var clickPosition : Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row,parent,false)

        return MyView(view)
    }

    override fun getItemCount(): Int {
    return dataSet.size
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.tvAnimales.text= dataSet[position]

        if(position == clickPosition){
            holder.tvAnimales.setTextColor(Color.BLACK)
            holder.tvAnimales.setBackgroundColor(Color.RED)
        }else{
            holder.tvAnimales.setTextColor(Color.WHITE)
            holder.tvAnimales.setBackgroundColor(Color.BLACK)
        }

        holder.tvAnimales.setOnClickListener{
            notifyItemChanged(clickPosition)
            clickPosition = position
            notifyItemChanged(clickPosition)

        }
    }

}