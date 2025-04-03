package com.example.imagenes

import android.os.Bundle
import android.view.View
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Guarreo : AppCompatActivity(), View.OnClickListener {
    lateinit var miSpinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    miSpinner.onItemSelectedListener(this)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}

private fun Spinner.onItemSelectedListener(guarreo: Guarreo) {

}
