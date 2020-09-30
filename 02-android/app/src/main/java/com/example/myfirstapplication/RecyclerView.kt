package com.example.myfirstapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdaptador(
    private val listaUsuarios: List<UsuarioHttp>,
    private val contexto: RecyclerViewActivity,
    private val recyclerView: androidx.recyclerview.widget.RecyclerView
) : androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerAdaptador.MyViewHolder>() {
    inner class MyViewHolder(view: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView = view.findViewById(R.id.txt_nombre)
        val cedulaTextView: TextView = view.findViewById(R.id.txt_cedula)
        val accionButton: Button = view.findViewById(R.id.btn_accion)
        val likeTextView: TextView = view.findViewById(R.id.txt_likes)
        var numeroLikes = 0

        init {
            accionButton.setOnClickListener {
                this.aniadirLike()
            }
        }

        fun aniadirLike() {
            this.numeroLikes = this.numeroLikes + 1
            likeTextView.text = this.numeroLikes.toString()
            contexto.aniadirLikesEnActividad()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdaptador.MyViewHolder {
        val itemView = LayoutInflater
            .from(
                parent.context
            )
            .inflate(
                R.layout.adaptador_persona,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listaUsuarios.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val usuario = listaUsuarios[position]
        holder.nombreTextView.text = usuario.nombre
        holder.cedulaTextView.text = usuario.cedula
        holder.accionButton.text = "Like ${usuario.nombre}"
        holder.likeTextView.text = "0"
    }
}