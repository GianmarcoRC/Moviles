package com.example.variasactividades

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.variasactividades.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var myActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        with(binding) {
            myActivityResultLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: androidx.activity.result.ActivityResult ->
                    if (result!!.resultCode == Activity.RESULT_OK) {
                        val miIntentResultado = result.data
                        txtMenBack.text = miIntentResultado!!.extras!!.getString("mensajeBack")
                    } else {
                        Toast.makeText(this@MainActivity, "MAl", Toast.LENGTH_SHORT).show()
                    }
                }
            btn1.setOnClickListener {
                var myIntent: Intent = Intent(this@MainActivity, Actividad2::class.java)
                myIntent.putExtra("Texto", editText.text.toString())
        myActivityResultLauncher.launch(myIntent)
            }
        }

    }
}