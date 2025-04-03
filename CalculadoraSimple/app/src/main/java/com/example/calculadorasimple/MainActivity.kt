package com.example.calculadorasimple

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    lateinit var etNum1 : EditText
    lateinit var etNum2 : EditText
    lateinit var resultado : TextView
    lateinit var btnSumar : Button
    lateinit var btnResta : Button
    lateinit var btnMultiplicar : Button
    lateinit var btnDividir : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
            etNum1 = findViewById<EditText>(R.id.etNumber1)
                etNum2 = findViewById<EditText>(R.id.etnumber2)
        resultado = findViewById<TextView>(R.id.Result)

         btnSumar = findViewById<Button>(R.id.btnSumar)
         btnResta = findViewById<Button>(R.id.btnResta)
         btnMultiplicar = findViewById<Button>(R.id.btnMulti)
        btnDividir = findViewById<Button>(R.id.btnDiv)

    btnSumar.setOnClickListener{
        //Recibo el texto lo paso a string y de String a double o nulo
        val num1 = etNum1.text.toString().toDoubleOrNull()
        val num2 = etNum2.text.toString().toDoubleOrNull()

        if (num1 !=null && num2 != null){
            resultado.text = "Resultado : ${num1 + num2}"
        }else {
            resultado.text = "Ingresa numeros validos"
        }
    }
        btnResta.setOnClickListener{
            //Recibo el texto lo paso a string y de String a double o nulo
            val num1 = etNum1.text.toString().toDoubleOrNull()
            val num2 = etNum2.text.toString().toDoubleOrNull()

            if (num1 !=null && num2 != null){
                resultado.text =  "Resultado : ${num1 - num2}"
            }else {
                resultado.text = "Ingresa numeros validos"
            }
        }
        btnMultiplicar.setOnClickListener{
            //Recibo el texto lo paso a string y de String a double o nulo
            val num1 = etNum1.text.toString().toDoubleOrNull()
            val num2 = etNum2.text.toString().toDoubleOrNull()

            if (num1 !=null && num2 != null){
                resultado.text = "Resultado : ${num1 * num2}"
            }else {
                resultado.text = "Ingresa numeros validos"
            }
        }
        btnDividir.setOnClickListener{
            //Recibo el texto lo paso a string y de String a double o nulo
            val num1 = etNum1.text.toString().toDoubleOrNull()
            val num2 = etNum2.text.toString().toDoubleOrNull()

            if (num1 !=null && num2 != null){
                resultado.text = "Resultado : ${num1 / num2}"
            }else {
                resultado.text = "Ingresa numeros validos"
            }
        }
    }

}