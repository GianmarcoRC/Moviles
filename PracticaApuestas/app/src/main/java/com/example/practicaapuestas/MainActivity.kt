package com.example.practicaapuestas

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.practicaapuestas.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var saldo: Int = 100
    private var modoJuego: String = "parImpar"
    private var opcionSeleccionada: String = ""
/*
    lateinit var animationDra: AnimationDrawable
*/

    private lateinit var job: Job
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
        job = Job()
        cargarJuego()
        binding.btnLanzar.setOnClickListener {
            lanzarDados(view)
        }
    }
    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
    private fun cargarJuego() {
        with(binding) {
            txtSaldo.text = saldo.toString()

            txtApuesta.inputType = android.text.InputType.TYPE_CLASS_NUMBER

            toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
                if (isChecked) {
                    when (checkedId) {
                        R.id.btnParImpar -> modoJuego = "parImpar"
                        R.id.btnMayorMenor -> modoJuego = "mayorMenor"
                    }
                    actualizarSpinner()
                }
            }

            btnParImpar.isChecked = true
            actualizarSpinner()
            imgDados.visibility = View.GONE
            imgResultado.visibility = View.GONE
        }
    }

    private fun actualizarSpinner() {
        val opciones = when (modoJuego) {
            "parImpar" -> resources.getStringArray(R.array.ParOImpar)
            else -> resources.getStringArray(R.array.MayorOMenor)
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.spinnerOpciones.adapter = adapter

        binding.spinnerOpciones.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    opcionSeleccionada = binding.spinnerOpciones.selectedItem.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    opcionSeleccionada = ""
                }
            }
    }

    private fun validarApuesta(apuesta: Int): Boolean {
        if (opcionSeleccionada.isEmpty() || opcionSeleccionada == "Seleccione una opción") {
            Snackbar.make(binding.main, "Por favor, seleccione una opción válida", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (apuesta <= 0) {
            Snackbar.make(binding.main, "Introduce un numero mayor de 0", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (apuesta > saldo) {
            Snackbar.make(binding.main, "Introduce una apuesta más baja", Snackbar.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private suspend fun mostrarDialogoContinuar() {
        withContext(Dispatchers.Main) {
            AlertDialog.Builder(this@MainActivity)
                .setMessage("¿Deseas continuar jugando?")
                .setPositiveButton("Sí") { dialog, _ ->
                    binding.imgDados.visibility = View.GONE
                    binding.imgResultado.visibility = View.GONE
                    binding.txtApuesta.text?.clear()
                }
                .setNegativeButton("No") { _, _ ->
                    finish()
                }
                .show()
        }
    }
    private suspend fun mostrarDialogoBancarrota() {
        withContext(Dispatchers.Main) {
            binding.imgResultado.setImageResource(R.drawable.quiebra_e1421208011881)
            AlertDialog.Builder(this@MainActivity)
                .setTitle("¡Bancarrota!")
                .setMessage("Te has quedado sin saldo. Fin del juego.")
                .setPositiveButton("Salir") { _, _ ->
                    finish()
                }

                .show()
        }
    }

    fun lanzarDados(v: View) {
        val apuestaStr = binding.txtApuesta.text.toString()
//El return es como un break es necesario porque si no al darle al boton se cierra la app
        if (apuestaStr.isEmpty() || opcionSeleccionada.isEmpty() || opcionSeleccionada == "Seleccione una opción") {
            Snackbar.make(binding.main, "Por favor, haga una apuesta y seleccione una opción", Snackbar.LENGTH_LONG).show()
            return
        }

        val apuesta = apuestaStr.toInt()
        if (!validarApuesta(apuesta)) {
            return
        }

     job = lifecycleScope.launch {
            try {
                binding.btnLanzar.isEnabled = false
                binding.imgDados.visibility = View.VISIBLE
                binding.imgResultado.visibility = View.GONE

                Glide.with(this@MainActivity).asGif().load(R.drawable.dado_imagen_animada_0092).into(binding.imgDados)

                delay(3000)

                val dado1 = (1..6).random()
                val dado2 = (1..6).random()
                val suma = dado1 + dado2

                binding.txtResultado1.text = "Dado 1: $dado1"
                binding.txtResultado2.text = "Dado 2: $dado2"

                val ganado = when (modoJuego) {
                    "parImpar" -> (suma % 2 == 0 && opcionSeleccionada == "Par") ||
                            (suma % 2 != 0 && opcionSeleccionada == "Impar")
                    else -> (suma > 7 && opcionSeleccionada == "Mayor que 7") ||
                            (suma < 7 && opcionSeleccionada == "Menor que 7")
                }

                saldo += if (ganado) apuesta else -apuesta
                binding.txtSaldo.text = saldo.toString()

                binding.imgDados.visibility = View.GONE

                binding.imgResultado.setImageResource(
                    if (ganado) R.drawable.ganar_dados else R.drawable.lose_logo_73_6494
                )
                binding.imgResultado.visibility = View.VISIBLE

                Snackbar.make(
                    binding.main,
                    if (ganado) "¡Has ganado!" else "Has perdido",
                    Snackbar.LENGTH_LONG
                ).show()

                binding.btnLanzar.isEnabled = true

                if (saldo <= 0) {
                    mostrarDialogoBancarrota()
                } else {
                    delay(1500)
                    mostrarDialogoContinuar()
                }
            } catch (e: Exception) {
                binding.btnLanzar.isEnabled = true
                Snackbar.make(binding.main, "Ocurrió un error: ${e.message}", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
