package com.example.api_universidad

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.beust.klaxon.Klaxon
import com.github.kittinunf.result.Result
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_estudiante.*
import kotlinx.android.synthetic.main.activity_estudiante.btn_nuevo
import kotlinx.android.synthetic.main.activity_universidad.*
import kotlinx.android.synthetic.main.dialos_universidad.view.*
import java.util.*
import kotlin.collections.ArrayList

class EstudianteActivity : AppCompatActivity() {

    private lateinit var adaptador: ArrayAdapter<Estudiante>
    private var idUniversidad: Int = -1
    private lateinit var listaEstudiantes: ArrayList<Estudiante>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiante)

        idUniversidad = intent.getIntExtra("idUniversidad", -1)
//        txt_descripcion_universidad.setText("Gestione los estudiantes de la \n universidad ${ServicioBDD.universidades[idUniversidad].nombre}")
        setearListaInicial()
        mostrarOpcionesListView()

        btn_nuevo.setOnClickListener {
            irACrearEstudiante(idUniversidad)
        }

    }

    fun setearListaInicial() {
        ServicioBDD
            .findStudentsByUniversity(idUniversidad)
            .responseString { request, response, result ->
                when (result) {
                    is Result.Success -> {
                        val stringEstudiantes = result.get()
                        val respuesta = Klaxon()
                            .converter(ServicioBDD.convertirEstudiante)
                            .parseArray<Estudiante>(stringEstudiantes)
                        listaEstudiantes = respuesta as ArrayList<Estudiante>

                        runOnUiThread {
                            adaptador = ArrayAdapter(
                                this,
                                android.R.layout.simple_list_item_1,
                                listaEstudiantes
                            )
                            list_view_estudiantes.adapter = adaptador
                        }
                    }
                    is Result.Failure -> {
                        val ex = result.getException()
                        Log.i("http-klaxon", "Error http ${ex.message}")
                    }
                }
            }
    }

    fun mostrarOpcionesListView() {
        val dialog = BottomSheetDialog(this)
        val mostrar = layoutInflater.inflate(R.layout.dialog_estudiante, null)
        dialog.setContentView(mostrar)
        list_view_estudiantes.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val idEstudiante = listaEstudiantes[position].id
                dialog.show()
                mostrar.txt_editar.setOnClickListener {
                    irACrearEstudiante(idUniversidad, idEstudiante)
                    dialog.dismiss()
                }
                mostrar.txt_eliminar.setOnClickListener {
                    eliminarEstudiante(idEstudiante)
                    dialog.dismiss()
                }
                mostrar.txt_ver_mas.setOnClickListener {
                    verMasInformacion(idEstudiante)
                    dialog.dismiss()
                }
            }
    }

    fun eliminarEstudiante(idEstudiante: Int) {
        ServicioBDD
            .deleteOne("estudiante", idEstudiante)
            .responseString{
                request, response, result ->
                when (result) {
                    is Result.Success -> {
                        val respuesta = result.get()
                        runOnUiThread {
                            refrescarTabla(adaptador)
                            setearListaInicial()
                            mostrarToast("Estudiante eliminado")
                        }
                    }
                    is Result.Failure -> {
                        val ex = result.getException()
                        Log.i("http-klaxon", "Error http ${ex.message}")
                    }
                }
            }
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

    fun verMasInformacion(idEstudiante: Int) {
        ServicioBDD
            .findById("estudiante", idEstudiante)
            .responseString { request, response, result ->
                when (result) {
                    is Result.Success -> {
                        val stringEstudiante = result.get()
                        val estudiante = Klaxon()
                            .converter(ServicioBDD.convertirEstudiante)
                            .parse<Estudiante>(stringEstudiante)!!
                        val infoEstudiante = "Nombre: ${estudiante.nombre}\n" +
                                "Fecha de nacimiento: ${estudiante.fechaNacimiento}\n" +
                                "${estudiante.calcularEdad(Date(estudiante.fechaNacimiento))}\n" +
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
                        runOnUiThread {
                            val dialog = mBuilder.create()
                            dialog.show()
                        }
                    }
                    is Result.Failure -> {
                        val ex = result.getException()
                        Log.i("http-klaxon", "Error http ${ex.message}")
                    }
                }
            }
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
            Calendar.YEAR
        )}"
    }

}