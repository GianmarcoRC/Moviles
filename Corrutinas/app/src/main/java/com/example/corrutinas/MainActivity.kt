package com.example.corrutinas

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.corrutinas.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var corrutina: Job
    /*private lateinit var corrutineHijo:Job*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
/*
        setContentView(R.layout.activity_main)
*/
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        with(binding){
            btToast.setOnClickListener{miToast("Hola que hace")}
            btSinHilos.setOnClickListener{tarealarga(pB1,myTV)}
            btConHilos.setOnClickListener{
                corrutina = lifecycleScope.launch { tarealargakotlin(pB1,myTV)
                /*corrutineHijo = lifecycleScope.launch { tarealargakotlin2(pB1,myTV) }*/}
                myTV.text = "Tarea en curso"
            }
            btStop.setOnClickListener{
                corrutina.cancel()
                myTV.text = "Tarea cancelada"
            }
        }


    }
    fun miToast(mensaje : String){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
  fun tarealarga(pb1 : ProgressBar,myTv:TextView){
      pb1.max = 100
      pb1.progress = 0
      for (i in 1 .. 10){
          pb1.progress = i*10
          Thread.sleep(1000)
      }
      miToast("Tarea finalizada")
      myTv.text = "Tarea finalizada"
  }
    suspend fun tarealargakotlin(pb1 : ProgressBar,myTv:TextView){
        pb1.max = 100
        pb1.progress = 0
        for (i in 1 .. 10){
            pb1.progress = i*10
            delay(1000)
        }
        miToast("Tarea finalizada")
        myTv.text = "Tarea finalizada"    }
    suspend fun tarealargakotlin2(pb1 : ProgressBar,myTv:TextView){
       delay(15000)
        miToast("Tarea finalizada")
       }
}