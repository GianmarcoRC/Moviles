package com.example.calculadorasimple

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding

class OtroMain : AppCompatActivity(), View.OnClickListener {
    lateinit var btn01:Button
    lateinit var txt : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.guarreo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)){ v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left,systemBars.top,systemBars.right,systemBars.bottom)
            insets
        }
        btn01 = findViewById(R.id.btn1)
        txt = findViewById(R.id.txt1)

        btn01.setOnClickListener(this)



    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
        txt.text = "Hola 2"
        txt.setTextColor(Color.BLUE)
    }

}