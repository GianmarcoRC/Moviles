package com.example.actividad2

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var btnRandom: Button
    lateinit var txtRandom: TextView
    lateinit var divisible2: CheckBox
    lateinit var divisible3: CheckBox
    lateinit var divisible5: CheckBox
    lateinit var divisible10: CheckBox
    lateinit var ninguno: CheckBox
    lateinit var btnComprobar: Button
    lateinit var resultado: TextView
    lateinit var resultImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnRandom = findViewById(R.id.btnRandom)
        txtRandom = findViewById(R.id.txtRandom)
        divisible2 = findViewById(R.id.cBox2)
        divisible3 = findViewById(R.id.cBox3)
        divisible5 = findViewById(R.id.cBox5)
        divisible10 = findViewById(R.id.cBox10)
        ninguno = findViewById(R.id.cBoxNinguno)
        btnComprobar = findViewById(R.id.btnComprobar)
        resultado = findViewById(R.id.txtResult)
        resultImage = findViewById(R.id.resultImage)

        btnRandom.setOnClickListener {
            val numrandom = (1000..2000).random()
            txtRandom.text = numrandom.toString()
        }

        btnComprobar.setOnClickListener {
            val num = txtRandom.text.toString().toIntOrNull()

            if (num == null) {
                resultado.text = "Por favor, genera un número primero."
                resultImage.setImageResource(0)
            } else if (!divisible2.isChecked && !divisible3.isChecked && !divisible5.isChecked && !divisible10.isChecked && !ninguno.isChecked) {
                resultado.text = "Debe escoger al menos una opción"
                resultImage.setImageResource(0)
            } else {
                var correcto = true

                if (divisible2.isChecked && num % 2 != 0) correcto = false
                if (divisible3.isChecked && num % 3 != 0) correcto = false
                if (divisible5.isChecked && num % 5 != 0) correcto = false
                if (divisible10.isChecked && num % 10 != 0) correcto = false

                val esDivisible = (num % 2 == 0 || num % 3 == 0 || num % 5 == 0 || num % 10 == 0)
                if (ninguno.isChecked && esDivisible) correcto = false
                if (!ninguno.isChecked && !esDivisible) correcto = false

                if (correcto) {
                    resultado.text = "Correcto"
                    resultImage.setImageResource(R.drawable.symbol_ok_svg)
                } else {
                    resultado.text = "Error"
                    resultImage.setImageResource(R.drawable.error)
                }
            }
        }

        ninguno.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                divisible2.isChecked = false
                divisible3.isChecked = false
                divisible5.isChecked = false
                divisible10.isChecked = false
            }
        }
    }
}
