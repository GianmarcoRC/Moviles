package com.example.reciclesview

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
import com.example.reciclesview.databinding.ActivityMainBinding
import com.example.reciclesview.recycler.MyAdapter
import com.example.reciclesview.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myviewModel: MainViewModel by viewModels()
    lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        with(binding) {
            myviewModel.devuelveArray()
            val mLayout = LinearLayoutManager(this@MainActivity)
            rvAnimales.layoutManager = mLayout

            myviewModel.datos.observe(this@MainActivity){
                myAdapter = MyAdapter (it)

                rvAnimales.adapter = myAdapter
                val midividerItemDecoration = DividerItemDecoration(
                    rvAnimales.getContext(),mLayout.orientation
                )
                rvAnimales.addItemDecoration(midividerItemDecoration)
            }
            btnDelete.setOnClickListener{
                if(myAdapter.clickPosition <0){
                    Toast.makeText(applicationContext,"Debe seleccionar una fila",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                myviewModel.delete(myAdapter.clickPosition)
            }
            myviewModel.delete.observe(this@MainActivity){
                myAdapter.notifyItemRemoved(it.position)
                myAdapter.clickPosition = RecyclerView.NO_POSITION
                myAdapter.notifyItemRangeChanged(0,it.animales.size)
            }
            btnAdd.setOnClickListener{
                var position = myAdapter.clickPosition;
                if(position <0){
                    position=0
                }
                myviewModel.add(position, txtname.text.toString())

            }
            myviewModel.add.observe(this@MainActivity){
                myAdapter.notifyItemInserted(it.position)
                myAdapter.notifyItemRangeChanged(0,it.animales.size)
                txtname.text.clear()
            }
        }
    }
}