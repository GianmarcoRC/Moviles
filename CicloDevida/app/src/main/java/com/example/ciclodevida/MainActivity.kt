package com.example.ciclodevida

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ciclodevida.Model.Datos
import com.example.ciclodevida.ViewModel.MainViewModel
import com.example.ciclodevida.ViewModel.MainViewModelFlows
import com.example.ciclodevida.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var numclicks = 0
    private val myViewModel: MainViewModel by viewModels()
    private val mainViewModelFlow: MainViewModelFlows by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        /*setContentView(R.layout.activity_main)*/
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        with(binding) {
            btnSumar.setOnClickListener{
                mainViewModelFlow.sumar(txtNumber.text.toString().toInt(),Datos(txtV.text.toString().toInt(),numclicks,false))
            }
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    mainViewModelFlow.datos.collect{
                        txtV.text= it.contator.toString()
                        numclicks = it.numClicks
                        if(it.mostrarmensaje==true){
                            Toast.makeText(this@MainActivity, "Has llegado a 5 clicks", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            txtV.text = "0"
//            btnSumar.setOnClickListener {
//                myViewModel.sumar(
//                    txtNumber.text.toString().toInt(),
//                    Datos(txtV.text.toString().toInt(), numclicks, false)
//                )
//            }
//            myViewModel.datos.observe(this@MainActivity){
//                txtV.text = it.contator.toString()
//                numclicks = it.numClicks
//                if(it.mostrarmensaje){
//                    Toast.makeText(this@MainActivity, "Has llegado a 5 clicks", Toast.LENGTH_SHORT).show()
//                }
//            }
        }
    }


//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putString("Contador",binding.txtV.text.toString())
//        outState.putInt("numclicks",numclicks)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        numclicks = savedInstanceState.getInt("numclicks")
//        binding.txtV.text = savedInstanceState.getString("Contador")
//    }
}