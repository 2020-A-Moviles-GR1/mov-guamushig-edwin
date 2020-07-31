package com.example.api_universidad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_estudiante.*
import java.lang.Error

class EstudianteActivity : AppCompatActivity() {

    private lateinit var adaptador: ArrayAdapter<Estudiante>
    private var idUniversidad: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiante)

        idUniversidad = intent.getIntExtra("idUniversidad", -1)
        val listaEstudiantes = ServicioBDD.retornarEstudiantesPorUniversidad(idUniversidad)

        txt_descripcion_universidad.setText("Gestione los estudiantes de la \n universidad ${ServicioBDD.universidades[idUniversidad].nombre}")
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaEstudiantes
        )

        list_view_estudiantes.adapter = adaptador

        btn_nuevo.setOnClickListener {
            irACrearEstudiante(idUniversidad)
        }

    }

    fun eliminarEstudiante(idEstudiante: Int) {
        ServicioBDD.eliminarEstudiante(idEstudiante)
        refrescarTabla(adaptador)
        mostrarToast("Estudiante eliminado")
    }

    fun irACrearEstudiante(idUniversidad: Int, idEstudiante: Int = -1) {
        val intentExplicito = Intent(
            this,
            CrearEditarEstudianteActivity::class.java
        )
        intentExplicito.putExtra("idUniversidad", idUniversidad)
        if (idEstudiante != -1) {
            intentExplicito.putExtra("idEstudiante", idEstudiante)
        }
        startActivityForResult(intentExplicito, 301)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    301 -> {
                        refrescarTabla(adaptador)
                    }
                }
            }
            Activity.RESULT_CANCELED -> {
            }
        }
    }

    fun refrescarTabla(
        adaptador: ArrayAdapter<Estudiante>
    ) {
        adaptador.notifyDataSetChanged()
    }

    fun mostrarToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}