package com.example.myfirstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_b_list_view.*

class BListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_list_view)
        val listaEntrenadores = arrayListOf<Entrenador>()
        listaEntrenadores.add(Entrenador("Edwin", "Guamushig"))
        listaEntrenadores.add(Entrenador("Paul", "Gualotunia"))
        listaEntrenadores.add(Entrenador("Jaime", "Kaviedes"))
        listaEntrenadores.add(Entrenador("Federico", "Laurito"))
        listaEntrenadores.add(Entrenador("Mauricio", "Fernandez"))

        val adaptador = ArrayAdapter( // podremos visualizar en la interfaz un componente grafico
            this,
            android.R.layout.simple_list_item_1,
            listaEntrenadores
        )

        lv_numeros.adapter = adaptador

        lv_numeros
            .onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.i("list-view", "Position $position")
        }

        btn_aniadir_entrenador.setOnClickListener {
            aniadirEntrenador(adaptador, listaEntrenadores)
        }
    }

    fun aniadirEntrenador(
        adaptador: ArrayAdapter<Entrenador>,
        listaEntrenadores: ArrayList<Entrenador>
    ) {
        listaEntrenadores.add(
            Entrenador("Ismael", "Heredia")
        )
        adaptador.notifyDataSetChanged()
    }
}