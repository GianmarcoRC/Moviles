package com.example.practicareciclerview1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicareciclerview1.databinding.ActivityPracticaRecicler1Binding
import com.example.practicareciclerview1.recycler.MyAdapter
import com.example.practicareciclerview1.viewmodel.MainViewModel

class PracticaRecicler1 : AppCompatActivity() {
    private lateinit var binding: ActivityPracticaRecicler1Binding
    private val myviewModel: MainViewModel by viewModels()
    lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPracticaRecicler1Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            myviewModel.devuelveArray()

            val mLayout = LinearLayoutManager(this@PracticaRecicler1)
            rvColores.layoutManager = mLayout

            myviewModel.datos.observe(this@PracticaRecicler1) {
                myAdapter = MyAdapter(it)
                rvColores.adapter = myAdapter

                val midividerItemDecoration = DividerItemDecoration(
                    rvColores.context, mLayout.orientation
                )
                rvColores.addItemDecoration(midividerItemDecoration)


            }

            btnDelete.setOnClickListener {
                if (myAdapter.clickPosition < 0) {
                    Toast.makeText(applicationContext, "Debe seleccionar una fila", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                myviewModel.delete(myAdapter.clickPosition)
            }

            myviewModel.delete.observe(this@PracticaRecicler1) {
                myAdapter.notifyItemRemoved(it.position)
                myAdapter.clickPosition = RecyclerView.NO_POSITION
                myAdapter.notifyItemRangeChanged(0, it.colores.size)
                rvColores.scheduleLayoutAnimation()
            }

            btnAdd.setOnClickListener {
                var position = myAdapter.clickPosition
                if (position < 0) {
                    position = 0
                }
                myviewModel.add(position, txtHexa.text.toString(), txtColor.text.toString())
            }

            myviewModel.add.observe(this@PracticaRecicler1) {
                myAdapter.notifyItemInserted(it.position)
                myAdapter.notifyItemRangeChanged(0, it.colores.size)
                txtHexa.text.clear()
                txtColor.text.clear()
                rvColores.scheduleLayoutAnimation()

            }
        }
    }
}
