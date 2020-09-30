package com.example.api_universidad

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.activity_crear_editar_universidad.*
import kotlinx.android.synthetic.main.activity_crear_editar_universidad.btn_cancelar
import kotlinx.android.synthetic.main.activity_crear_editar_universidad.btn_guardar
import com.github.kittinunf.result.Result


class CrearEditarUniversidadActivity : AppCompatActivity() {

    private var idUniversidad: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_editar_universidad)


        idUniversidad = intent.getIntExtra("id", -1)

        if (idUniversidad != -1) {
            ServicioBDD
                .findById("universidad", idUniversidad)
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            val stringUniversidade = result.get()
                            val respuesta = Klaxon()
                                .converter(ServicioBDD.convertirUniversidad)
                                .parse<Universidad>(stringUniversidade)
                            val universidad = respuesta as Universidad
                            runOnUiThread {
                                setearCheckbox(universidad.estado!!)
                                llenarCamposEditar(universidad)
                            }
                        }
                        is Result.Failure -> {
                            val ex = result.getException()
                            Log.i("http-klaxon", "Error http ${ex.message}")
                        }
                    }
                }
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
        Log.i("http-kl", "Creando universidad")

        val nombre = plain_txt_nombre.text.toString()
        val categoria = txt_categoria.text.toString()
        val fundacion = plain_txt_fundacion.text.toString()
        val area = plain_text_area.text.toString()
        val estado = ayuda_check.text.toString()
        val universidad: MutableList<Pair<String, Any>> = mutableListOf(
            "nombre" to nombre,
            "categoria" to categoria,
            "fundacion" to fundacion.toInt(),
            "areaConstruccion" to area.toDouble()
        )
        if (idUniversidad == -1) {
            ServicioBDD
                .createOne("universidad", universidad)
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            runOnUiThread {
                                val stringUniversidade = result.get()
                                Log.i("http-kl", stringUniversidade)
                                mostrarToast("Universidad creada")
                            }
                        }
                        is Result.Failure -> {
                            val ex = result.getException()
                            Log.i("http-klaxon", "Error http ${ex.message}")
                        }
                    }
                }
        } else {
            universidad.add("estado" to estado.toInt())
            ServicioBDD
                .updateOne("universidad", idUniversidad, universidad)
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            runOnUiThread {
                                val stringUniversidade = result.get()
                                Log.i("http-kl", stringUniversidade)
                                mostrarToast("Universidad editada")
                            }
                        }
                        is Result.Failure -> {
                            val ex = result.getException()
                            Log.i("http-klaxon", "Error http ${ex.message}")
                        }
                    }
                }
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
        titulo_check.visibility = View.VISIBLE
        check_estado.visibility = View.VISIBLE

        if (universidad.estado == 1) {
            check_estado.setChecked(true)
        }
    }

    fun setearCheckbox(estado: Int) {
        if (estado == 1) {
            ayuda_check.setText("1")
        } else {
            ayuda_check.setText("0")
        }
        check_estado.setOnClickListener {
            val estado = check_estado.isChecked()
            if (estado) {
                ayuda_check.setText("1")
            } else {
                ayuda_check.setText("0")
            }
        }
    }

    fun mostrarToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}