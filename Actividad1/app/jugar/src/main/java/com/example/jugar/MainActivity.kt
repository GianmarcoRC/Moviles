package com.example.jugar

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.jugar.R.id.txt1
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(){
lateinit var texto : TextView
lateinit var cordinator : CoordinatorLayout
lateinit var imgView : ImageView
lateinit var animationDra : AnimationDrawable
/*
lateinit var mivista:View
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        texto = findViewById(txt1)
        cordinator = findViewById(R.id.cordl)
        imgView = findViewById(R.id.imgV)
        imgView.setBackgroundResource(R.drawable.tragaperras)
        animationDra = imgView.background as AnimationDrawable
        Glide.with(this).load(R.drawable.dado_imagen_animada_0092).into(imgView);

    }
    fun miToast(v:View){
        var mitoast : Toast= Toast.makeText(this, "Hola caracola", Toast.LENGTH_LONG)
        mitoast.show()
        animationDra.start()

    }
    fun snackBar(v:View){
        Snackbar.make(cordinator, "Esto es un Snackbar", Snackbar.LENGTH_LONG).setAction("Action",View.OnClickListener {
            texto.text = "Accion"
        }).setActionTextColor(Color.RED).show()
        animationDra.stop()
    }
    fun alert(v:View){
        var myAlert = AlertDialog.Builder(this)
        myAlert.setTitle("Alerta")
        myAlert.setMessage("Esta es una alerta")
        myAlert.setPositiveButton("OK", DialogInterface.OnClickListener({
            _, _ -> texto.text="Has pulsado OK"
        }))
        myAlert.setNegativeButton("Cancelar", DialogInterface.OnClickListener({
            _, _ -> this.finish()
        }))
        myAlert.setNeutralButton("Esto es un boton mucho mas largooooooooo", null)
        myAlert.create().show()
    }
fun customAlert(v:View){
    var myAlert = AlertDialog.Builder(this)
    myAlert.setTitle("Alerta")
/*
    myAlert.setMessage("Esta es una alerta con custom view")
*/
  var mivista = layoutInflater.inflate(R.layout.loggin, null)
    myAlert.setView(mivista)
    myAlert.setPositiveButton("OK", DialogInterface.OnClickListener({
        _, _ -> texto.text=mivista.findViewById<EditText>(R.id.username).text
    }))
    myAlert.setNegativeButton("Cancelar", DialogInterface.OnClickListener({
        _, _ -> this.finish()
    }))
    myAlert.create().show()
}


}