package com.example.mifragmento

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn = findViewById<Button>(R.id.btnfr)
        btn.setOnClickListener {
            val edit = findViewById<EditText>(R.id.editTextText)
         val myFragmentManager: FragmentManager = supportFragmentManager
            val myTrasctionFragment: FragmentTransaction = myFragmentManager.beginTransaction()
            val fragment : FirstFragment = FirstFragment.newInstance(edit.text.toString())
            myTrasctionFragment.replace(R.id.frame,fragment).commit()
        }
    }
}