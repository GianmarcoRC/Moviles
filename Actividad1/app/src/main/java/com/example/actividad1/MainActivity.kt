package com.example.actividad1

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var bRandom: Button
    lateinit var comprobar: Button
    lateinit var numero : TextView
    lateinit var resultado : TextView
    lateinit var fondo : Switch
    lateinit var btnsi : RadioButton
    lateinit var btnno : RadioButton
    var pulsado : Boolean = false
    lateinit var mainActivity: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mainActivity = findViewById(R.id.main)
        fondo = findViewById(R.id.sFondo)
        bRandom = findViewById(R.id.btnRandom)
        comprobar = findViewById(R.id.btnComprobar)
        numero = findViewById(R.id.txtRandom)
        resultado = findViewById(R.id.txtComprobacion)
        btnsi = findViewById(R.id.rbtnSi)
        btnno = findViewById(R.id.rbtnNo)
        bRandom.setOnClickListener {
            val numrandom = (1900..2500).random()
            numero.text = numrandom.toString()
        }
        comprobar.setOnClickListener {
            var anioText = numero.text.toString()
            if (anioText.isNotEmpty()) {
                val anio = anioText.toInt()
                if (btnsi.isChecked && esBisiesto(anio) || btnno.isChecked && !esBisiesto(anio)) {
                    resultado.text = "Correcto"
                    resultado.setTextColor(Color.GREEN)
                } else if (btnsi.isChecked && !esBisiesto(anio) || btnno.isChecked && esBisiesto(
                        anio
                    )
                ) {
                    resultado.text = "Error"
                    resultado.setTextColor(Color.RED)
                } else {
                    resultado.text = "Debe escoger una de las opciones"
                    resultado.setTextColor(Color.BLUE)
                }
            } else {
                resultado.text = "Genera un numero"
                resultado.setTextColor(Color.BLUE)
            }
        }
        fondo.setOnClickListener() {
            if (fondo.isChecked) {
                mainActivity.setBackgroundColor(Color.YELLOW)
            } else {
                mainActivity.setBackgroundColor(Color.WHITE)
            }
        }

    }
    fun esBisiesto(anio: Int): Boolean {
        return (anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0)
    }

}