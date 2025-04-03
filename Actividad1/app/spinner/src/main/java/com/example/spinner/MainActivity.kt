package com.example.spinner

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get

class MainActivity : AppCompatActivity() {
    lateinit var temas : Spinner
    lateinit var tiposTemas : Spinner
    lateinit var imagen : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        temas  = findViewById(R.id.sp1)
        tiposTemas = findViewById(R.id.sp2)
        imagen = findViewById(R.id.imageView)

        val temasAdapter = ArrayAdapter.createFromResource(this, R.array.temas, android.R.layout.simple_spinner_item)
        temas.adapter = temasAdapter
        temas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val temaSeleccionado = temas.selectedItem.toString()
                actualizarTiposTemas(temaSeleccionado)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        tiposTemas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               if (tiposTemas.selectedItem.toString() == "Breaking bad"){
                   imagen.setImageResource(R.drawable.breaking_bad_logo_svg)
               }else if (tiposTemas.selectedItem.toString() == "Juego de tronos"){
                   imagen.setImageResource(R.drawable.juego)
               }else if (tiposTemas.selectedItem.toString() == "Dragon ball"){
                   imagen.setImageResource(R.drawable.dragon)
               }else if (tiposTemas.selectedItem.toString() == "Queen"){
                   imagen.setImageResource(R.drawable.queen)
               }else if (tiposTemas.selectedItem.toString() == "The beatles"){
                   imagen.setImageResource(R.drawable.betles)
               }else if (tiposTemas.selectedItem.toString() == "Nirvana"){
                   imagen.setImageResource(R.drawable.nirvana)
               }else if (tiposTemas.selectedItem.toString() == "Mushoku Tensei"){
                   imagen.setImageResource(R.drawable.mushoku_tensei_portada)
               }else if (tiposTemas.selectedItem.toString() == "One piece"){
                   imagen.setImageResource(R.drawable._1rehhwbubl)
               }else if (tiposTemas.selectedItem.toString() == "Solo leveling"){
                   imagen.setImageResource(R.drawable.solo)
               }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun actualizarTiposTemas(tema: String) {
        val array = when (tema) {
            "Serie" -> R.array.series
            "Grupo musica" -> R.array.Grupos
            "Anime" -> R.array.Anime
            else -> return
        }

        val tiposAdapter = ArrayAdapter.createFromResource(this, array, android.R.layout.simple_spinner_item)
        tiposTemas.adapter = tiposAdapter
    }
}


