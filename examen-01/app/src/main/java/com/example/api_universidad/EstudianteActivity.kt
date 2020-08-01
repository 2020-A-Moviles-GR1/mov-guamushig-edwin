package com.example.api_universidad

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_estudiante.*
import kotlinx.android.synthetic.main.activity_estudiante.btn_nuevo
import kotlinx.android.synthetic.main.activity_universidad.*
import kotlinx.android.synthetic.main.dialos_universidad.view.*
import java.util.*

class EstudianteActivity : AppCompatActivity() {

    private lateinit var adaptador: ArrayAdapter<Estudiante>
    private var idUniversidad: Int = -1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiante)

        idUniversidad = intent.getIntExtra("idUniversidad", -1)
        txt_descripcion_universidad.setText("Gestione los estudiantes de la \n universidad ${ServicioBDD.universidades[idUniversidad].nombre}")
        setearListaInicial()
        mostrarOpcionesListView()

        btn_nuevo.setOnClickListener {
            irACrearEstudiante(idUniversidad)
        }

    }

    fun setearListaInicial() {
        val listaEstudiantes = ServicioBDD.retornarEstudiantesPorUniversidad(idUniversidad)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaEstudiantes
        )
        list_view_estudiantes.adapter = adaptador
    }

    fun mostrarOpcionesListView() {
        val dialog = BottomSheetDialog(this)
        val mostrar = layoutInflater.inflate(R.layout.dialog_estudiante, null)
        dialog.setContentView(mostrar)
        list_view_estudiantes.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                dialog.show()
                mostrar.txt_editar.setOnClickListener {
                    irACrearEstudiante(idUniversidad, position)
                    dialog.dismiss()
                }
                mostrar.txt_eliminar.setOnClickListener {
                    eliminarEstudiante(position)
                    dialog.dismiss()
                }
                mostrar.txt_ver_mas.setOnClickListener {
                    val estudiante = ServicioBDD.estudiantes[position]
                    verMasInformacion(estudiante)
                    dialog.dismiss()
                }
            }
    }

    fun eliminarEstudiante(idEstudiante: Int) {
        ServicioBDD.eliminarEstudiante(idEstudiante)
        refrescarTabla(adaptador)
        setearListaInicial()
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

    fun verMasInformacion(estudiante: Estudiante) {
        val infoEstudiante = "Nombre: ${estudiante.nombre}\n" +
                "Fecha de nacimiento: ${transformarDataString(estudiante.fechaNacimiento)}\n" +
                "${estudiante.calcularEdad(estudiante.fechaNacimiento)}\n" +
                "Sexo: ${estudiante.sexo}\n" +
                "Estatura: ${estudiante.estatura}\n"
                "Tiene beca: ${estudiante.tieneBeca}\n"
        val mBuilder = AlertDialog.Builder(
            this
        )
        mBuilder.setTitle("Informacion de estudiante")
        mBuilder.setMessage(infoEstudiante)
        mBuilder.setNeutralButton("Aceptar") { dialog: DialogInterface?, which: Int ->
            dialog?.cancel()
        }
        val dialog = mBuilder.create()
        dialog.show()
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
                        setearListaInicial()
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

    fun transformarDataString(fecha: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = fecha
        return "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)}/${calendar.get(
            Calendar.YEAR)}"
    }

}