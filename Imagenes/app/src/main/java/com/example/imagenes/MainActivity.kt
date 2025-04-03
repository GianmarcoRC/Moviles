package com.example.imagenes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var miTextView: TextView
    lateinit var miImageButton: ImageButton
    lateinit var miImageView: ImageView
    val paises = arrayOf<String>("Paris","España","Japón")
    var miAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,paises)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        miImageButton = findViewById(R.id.imb1)
        miImageView = findViewById(R.id.imv1)

       /* miImageButton.setOnClickListener(View.OnClickListener {
            miImageView.setImageResource(R.drawable.sigue_trabajando)
        })*/
        miImageButton.setOnClickListener(this)
        miAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
    

    override fun onClick(v: View?) {
        miImageView.setImageResource(R.drawable.sigue_trabajando)
        Log.i("Mi primera app", "Mi primer mensaje")
    }
    }
