package com.example.variasactividades

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.variasactividades.databinding.ActivityActividad2Binding
import com.example.variasactividades.databinding.ActivityMainBinding

class Actividad2 : AppCompatActivity() {
    lateinit var binding:  ActivityActividad2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityActividad2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        with(binding){

            var myExtra : Bundle? = intent.extras
            var mensaje = myExtra!!.getString("Texto")
            btnSal.setOnClickListener{
                val myResultado = Intent()
                myResultado.putExtra("mensajeBack",txtRes.text.toString())
                setResult(RESULT_OK,myResultado)
                this@Actividad2.finish()
            }
            txtRes.text = mensaje
        }
    }
}