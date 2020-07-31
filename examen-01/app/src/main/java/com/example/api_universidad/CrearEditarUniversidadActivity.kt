package com.example.api_universidad

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_crear_editar_universidad.*

class CrearEditarUniversidadActivity : AppCompatActivity() {

    private  var idUniversidad: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_editar_universidad)


        idUniversidad = intent.getIntExtra("id", -1)

        if (idUniversidad != -1) {
            val universidad: Universidad = ServicioBDD.universidades[idUniversidad]
            llenarCamposEditar(universidad)
        }

        btn_modal_categoria.setOnClickListener {
            mostrarModalCategorias()
        }

        btn_guardar.setOnClickListener {
            guardarUniversidad()
            val intent = Intent()
            setResult(
                Activity.RESULT_OK,
                intent
            )
            finish()
        }

        btn_cancelar.setOnClickListener {
            finish()
        }
    }

    fun guardarUniversidad() {
        val nombre = plain_txt_nombre.text.toString()
        val categoria = txt_categoria.text.toString()
        val fundacion = plain_txt_fundacion.text.toString()
        val area = plain_text_area.text.toString()
        val universidad =
            (Universidad(nombre, fundacion.toInt(), categoria.single(), area.toFloat()))
        if (idUniversidad == -1) {
            ServicioBDD.aniadirUniversidad(universidad)
            mostrarToast("Universidad ${nombre} creada")
        } else {
            ServicioBDD.actualizarUniversidad(idUniversidad, universidad)
            mostrarToast("Universidad ${nombre} editada")
        }
    }

    fun mostrarModalCategorias() {
        val arrayCategorias = arrayOf("A", "B", "C", "D")
        val mBuilder = AlertDialog.Builder(
            this
        )
        mBuilder.setTitle("Elija la categoria de universidad")
        mBuilder.setSingleChoiceItems(
            arrayCategorias,
            -1
        ) { dialog: DialogInterface?, indice: Int ->
            val cateriaSeleccionada = arrayCategorias[indice]
            txt_categoria.setText(cateriaSeleccionada)
            btn_modal_categoria.text = cateriaSeleccionada
            dialog?.dismiss()
        }
        mBuilder.setNeutralButton("Cancelar") { dialog: DialogInterface?, which: Int ->
            dialog?.cancel()
        }
        val dialog = mBuilder.create()
        dialog.show()
    }

    fun llenarCamposEditar(universidad: Universidad) {
        plain_txt_nombre.setText(universidad.nombre)
        txt_categoria.setText(universidad.categoria.toString())
        plain_txt_fundacion.setText(universidad.fundacion.toString())
        plain_text_area.setText(universidad.areaConstruccion.toString())
        btn_modal_categoria.setText(universidad.categoria.toString())
    }

   fun mostrarToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }


}