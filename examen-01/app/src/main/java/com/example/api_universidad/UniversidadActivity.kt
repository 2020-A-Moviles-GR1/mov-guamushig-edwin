package com.example.api_universidad

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.Klaxon
import com.github.kittinunf.result.Result
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_universidad.*
import kotlinx.android.synthetic.main.dialos_universidad.view.*


class UniversidadActivity : AppCompatActivity() {

    private lateinit var adaptador: ArrayAdapter<Universidad>

    private lateinit var listaUniversidades: ArrayList<Universidad>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_universidad)

        cargarOpcionesListView()
        setearListaInicial()

        btn_nuevo.setOnClickListener {
            irACrearEditarUniversidad()
        }


    }

    fun setearListaInicial() {
        ServicioBDD.findAll("universidad")
            .responseString { request, response, result ->
                when (result) {
                    is Result.Success -> {
                        val stringUniversidades = result.get()
                        val respuesta = Klaxon()
                            .converter(ServicioBDD.convertirUniversidad)
                            .parseArray<Universidad>(stringUniversidades)
                        listaUniversidades = respuesta as ArrayList<Universidad>
                        runOnUiThread {
                            adaptador = ArrayAdapter(
                                this,
                                android.R.layout.simple_list_item_1,
                                listaUniversidades
                            )
                            list_view_universidades.adapter = adaptador
                        }
                    }
                    is Result.Failure -> {
                        val ex = result.getException()
                        Log.i("http-klaxon", "Error http ${ex.message}")
                    }
                }
            }
    }

    fun cargarOpcionesListView() {
        val dialog = BottomSheetDialog(this)
        val mostrar = layoutInflater.inflate(R.layout.dialos_universidad, null)
        dialog.setContentView(mostrar)

        list_view_universidades.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                dialog.show()
                val idUniversidad = listaUniversidades[position].id
                mostrar.txt_editar.setOnClickListener {
                    irACrearEditarUniversidad(idUniversidad)
                    dialog.dismiss()
                }
                mostrar.txt_eliminar.setOnClickListener {
                    eliminarUniversidad(idUniversidad)
                    dialog.dismiss()
                }
                mostrar.txt_ver_mas.setOnClickListener {
                    verMasInformacion(idUniversidad)
                    dialog.dismiss()
                }

                mostrar.txt_estudiantes.setOnClickListener {
                    irAGestionEstudiantes(idUniversidad)
                    dialog.dismiss()
                }
            }
    }

    fun irAGestionEstudiantes(idUniversidad: Int) {
        val intentExplicito = Intent(
            this,
            EstudianteActivity::class.java
        )
        intentExplicito.putExtra("idUniversidad", idUniversidad)
        startActivityForResult(intentExplicito, 301)
    }

    fun eliminarUniversidad(idUniversidad: Int) {
        ServicioBDD.deleteOne("universidad", idUniversidad)
            .responseString { request, response, result ->
                when (result) {
                    is Result.Success -> {
                        val respuesta = result.get()
                        runOnUiThread {
                            refrescarTabla(adaptador)
                            setearListaInicial()
                            mostrarToast("Universidad eliminada")
                        }
                    }
                    is Result.Failure -> {
                        val ex = result.getException()
                        Log.i("http-klaxon", "Error http ${ex.message}")
                    }
                }
            }
    }

    fun irACrearEditarUniversidad(idUniversidad: Int = -1) {
        val intentExplicito = Intent(
            this,
            CrearEditarUniversidadActivity::class.java
        )
        if (idUniversidad != -1) {
            intentExplicito.putExtra("id", idUniversidad)
        }
        startActivityForResult(intentExplicito, 300)
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
                    300 -> {
                        setearListaInicial()
                    }
                }
            }
            Activity.RESULT_CANCELED -> {
            }
        }
    }

    fun verMasInformacion(idUniversidad: Int) {
        ServicioBDD.findById("universidad", idUniversidad)
            .responseString { request, response, result ->
                when (result) {
                    is Result.Success -> {
                        val stringUniversidad = result.get()
                        val universidad = Klaxon()
                            .converter(ServicioBDD.convertirUniversidad)
                            .parse<Universidad>(stringUniversidad)!!

                        val infoUniversidad = "Nombre: ${universidad.nombre}\n" +
                                "Categoria: ${universidad.categoria}\n" +
                                "Fundacion: ${universidad.fundacion}\n" +
                                "Estado: ${stringEstadoActivoInactivo(universidad.estado!!)}\n" +
                                "Tamanio campus: ${universidad.areaConstruccion}\n"
                                    .trimIndent()

                        val mBuilder = AlertDialog.Builder(
                            this
                        )
                        mBuilder.setTitle("Información de universidad")
                        mBuilder.setMessage(infoUniversidad)
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


    fun refrescarTabla(
        adaptador: ArrayAdapter<Universidad>
    ) {
        adaptador.notifyDataSetChanged()
    }

    fun mostrarToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun stringEstadoActivoInactivo(estado: Int): String {
        if (estado == 0) {
            return "Inativo"
        }
        return "Activo"
    }
}