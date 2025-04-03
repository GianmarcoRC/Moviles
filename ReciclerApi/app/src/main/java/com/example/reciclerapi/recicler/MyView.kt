package com.example.reciclerapi.recicler

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.reciclerapi.R

class MyView (itemview: View): RecyclerView.ViewHolder(itemview){
    val imV1 = itemview.findViewById<View>(R.id.imV1) as ImageView
}