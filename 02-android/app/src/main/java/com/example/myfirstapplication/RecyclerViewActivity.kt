package com.example.myfirstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        val listaUsuario = arrayListOf<UsuarioHttp>();
        val usuario = UsuarioHttp(
            1,
            12345689,
            12345689,
            "1234567895",
            "Edwin",
            "edw@epn.edu.ec",
            "soltero",
            "1234"
        )
        val usuario2 = UsuarioHttp(
            2,
            12345689,
            12345689,
            "1234567896",
            "Paul",
            "paul@epn.edu.ec",
            "soltero",
            "12345"
        )
        val usuario3 = UsuarioHttp(
            2,
            12345689,
            12345689,
            "1234567897",
            "Katherine",
            "katty@epn.edu.ec",
            "soltero",
            "123465"
        )
        listaUsuario.add(usuario)
        listaUsuario.add(usuario2)
        listaUsuario.add(usuario3)

        iniciarRecyclerView(
            listaUsuario,
            this,
            rv_usuarios
        )
    }

    fun iniciarRecyclerView(
        listaUsuarios: List<UsuarioHttp>,
        actividad: RecyclerViewActivity,
        recyclerView: androidx.recyclerview.widget.RecyclerView
    ) {
        val adaptadorUsuario = RecyclerAdaptador(
            listaUsuarios,
            actividad,
            recyclerView
        )
        rv_usuarios.adapter = adaptadorUsuario
        rv_usuarios.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_usuarios.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(actividad)
        adaptadorUsuario.notifyDataSetChanged()
    }
}