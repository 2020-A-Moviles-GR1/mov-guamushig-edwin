package com.example.api_universidad

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isEmpty
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_crear_editar_universidad.*
import kotlinx.android.synthetic.main.activity_universidad.*
import kotlinx.android.synthetic.main.dialos_universidad.view.*

class UniversidadActivity : AppCompatActivity() {

    private lateinit var adaptador: ArrayAdapter<Universidad>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_universidad)

        btn_nuevo.setOnClickListener {
            irACrearUniversidad()
        }

        val listaUniversidades = ServicioBDD.universidades
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaUniversidades
        )
        list_view_universidades.adapter = adaptador

        val dialog = BottomSheetDialog(this)
        val mostrar = layoutInflater.inflate(R.layout.dialos_universidad, null)
        dialog.setContentView(mostrar)

        list_view_universidades.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                dialog.show()
                mostrar.txt_editar.setOnClickListener {
                    irACrearUniversidad(position)
                    dialog.dismiss()
                }
                mostrar.txt_eliminar.setOnClickListener {
                    eliminarUniversidad(position)
                    dialog.dismiss()
                }
                mostrar.txt_ver_mas.setOnClickListener {
                    verMasInformacion(position)
                    dialog.dismiss()
                }

                mostrar.txt_estudiantes.setOnClickListener {
                    irAGestionEstudiantes(position)
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
        ServicioBDD.eliminarUniversidad(idUniversidad)
        refrescarTabla(adaptador)
        mostrarToast("Universidad eliminada")
    }

    fun irACrearUniversidad(idUniversidad: Int = -1) {
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
                        refrescarTabla(adaptador)
                    }
                }
            }
            Activity.RESULT_CANCELED -> {
            }
        }
    }

    fun verMasInformacion(idUniversidad: Int) {
        val universidades = ServicioBDD.universidades
        val universidad = universidades[idUniversidad]
        val infoUniversidad = "Nombre: ${universidad.nombre}\n" +
                "Categoria: ${universidad.categoria}\n" +
                "Fundacion: ${universidad.fundacion}\n" +
                "Estado: ${universidad.estado}\n" +
                "Tamanio campus: ${universidad.areaConstruccion}\n"
        val mBuilder = AlertDialog.Builder(
            this
        )
        mBuilder.setTitle("Informacion de universidad")
        mBuilder.setMessage(infoUniversidad)
        mBuilder.setNeutralButton("Aceptar") { dialog: DialogInterface?, which: Int ->
            dialog?.cancel()
        }
        val dialog = mBuilder.create()
        dialog.show()
    }

    fun refrescarTabla(
        adaptador: ArrayAdapter<Universidad>
    ) {
        adaptador.notifyDataSetChanged()
    }

    fun mostrarToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}