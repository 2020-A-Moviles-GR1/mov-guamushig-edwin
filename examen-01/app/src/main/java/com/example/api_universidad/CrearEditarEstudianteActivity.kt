package com.example.api_universidad

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Instrumentation
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.activity_crear_editar_estudiante.*
import java.util.*
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_crear_editar_estudiante.btn_cancelar
import kotlinx.android.synthetic.main.activity_crear_editar_estudiante.btn_guardar
import kotlinx.android.synthetic.main.activity_crear_editar_estudiante.txt_nombre


class CrearEditarEstudianteActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private var idUniversidad: Int = -1
    private var idEstudiante: Int = -1
    private var dia = 0
    private var mes = 0
    private var anio = 0
    private var latitud = 0.0
    private var longitud = 0.0
    private var urlImagen = ""
    private var urlRedSocial = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_editar_estudiante)

        idUniversidad = intent.getIntExtra("idUniversidad", -1)
        txt_id_universidad.setText("${idUniversidad}")
        idEstudiante = intent.getIntExtra("idEstudiante", -1)

        if (idEstudiante != -1) {
            ServicioBDD
                .findById("estudiante", idEstudiante)
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            val stringEstudiante = result.get()
                            val estudiante = Klaxon()
                                .converter(ServicioBDD.convertirEstudiante)
                                .parse<Estudiante>(stringEstudiante)
                            runOnUiThread {
                                cargarConfiguracionInicial(estudiante!!)
                            }
                        }
                        is Result.Failure -> {
                        }
                    }
                }
        }
        mostrarDatePicker()
        setearRaddioButton()
        setearCheckbox()

        btn_guardar.setOnClickListener {
            guardar()
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

        btn_ubicacion.setOnClickListener {
            irAMapaEstudiante()
        }
    }

    fun irAMapaEstudiante() {
        val intent = Intent(
            this,
            MapaActivity::class.java
        )
        Log.i("latitud", "$latitud")
        if (idEstudiante != -1) {
            intent.putExtra("longitud", longitud)
            intent.putExtra("latitud", latitud)
            intent.putExtra("urlImagen", urlImagen)
            intent.putExtra("urlRedSocial", urlRedSocial)
        }
        startActivityForResult(intent, 300)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    300 -> {
                        latitud = data?.getDoubleExtra("latitud", 0.0)!!
                        longitud = data.getDoubleExtra("longitud", 0.0)
                    }
                }
            }
        }
    }

    fun guardar() {

        val idUniversidadtxt = txt_id_universidad.text.toString()
        val nombre = txt_nombre.text.toString()
        val fechaNac = txt_fecha_nacimiento.text.toString()
        val sexo = txt_sexo.text.toString()
        val estatura = txt_estatura.text.toString()
        val tieneBeca = txt_beca.text.toString()
        val urlImagenPerfil = txt_url_foto_perfil.text.toString()
        val urlFacebook = txt_red_social.text.toString()

        val estudiante: MutableList<Pair<String, Any>> = mutableListOf(
            "nombre" to nombre,
            "fechaNacimiento" to fechaNac,
            "sexo" to sexo,
            "estatura" to estatura.toDouble(),
            "tieneBeca" to tieneBeca.toInt(),
            "latitud" to latitud,
            "longitud" to longitud,
            "urlImagen" to urlImagenPerfil,
            "urlRedSocial" to urlFacebook,
            "universidad" to idUniversidadtxt.toInt()
        )


        if (idEstudiante == -1) {
            ServicioBDD
                .createOne("estudiante", estudiante)
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            runOnUiThread {
                                val stringEstudiante = result.get()
                                mostrarToast("Estudiante creado")
                            }
                        }
                        is Result.Failure -> {
                            val ex = result.getException()
                            Log.i("http-klaxon", "Error http ${ex.message}")
                        }
                    }
                }
        } else {
            ServicioBDD
                .updateOne("estudiante", idEstudiante, estudiante)
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            runOnUiThread {
                                val stringEstudiante = result.get()
                                mostrarToast("Estudiante editado")
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

    @SuppressLint("SetTextI18n")
    fun cargarConfiguracionInicial(estudiante: Estudiante) {
        latitud = estudiante.latitud
        longitud = estudiante.longitud
        txt_nombre.setText(estudiante.nombre)
        txt_fecha_nacimiento.setText(estudiante.fechaNacimiento)
        txt_estatura.setText(estudiante.estatura.toString())
        txt_url_foto_perfil.setText(estudiante.urlImagen)
        txt_red_social.setText(estudiante.urlRedSocial)
        urlImagen = estudiante.urlImagen
        urlRedSocial = estudiante.urlRedSocial
        rd_btn_masculino.isEnabled = false
        rd_btn_femenino.isEnabled = false
        btn_calendario.isEnabled = false
        if (estudiante.sexo == "M") {
            raddio_button_sexo.check(rd_btn_masculino.id)
            txt_sexo.setText("M")
        } else {
            raddio_button_sexo.check(rd_btn_femenino.id)
            txt_sexo.setText("F")
        }

        if (estudiante.tieneBeca == 1) {
            check_beca.setChecked(true)
        }

    }


    fun setearCheckbox() {
//        ayuda_check.setText("0")
        check_beca.setOnClickListener {
            val tieneBeca = check_beca.isChecked()
            if (tieneBeca) {
                txt_beca.setText("1")
            } else {
                txt_beca.setText("0")
            }
        }
    }

    fun setearRaddioButton() {
        raddio_button_sexo.setOnCheckedChangeListener { group, checkedId ->
            val seleccionoMasculino = checkedId == R.id.rd_btn_masculino
            val seleccionoFemenino = checkedId == R.id.rd_btn_femenino
            if (seleccionoMasculino) {
                txt_sexo.setText("M")
            } else if (seleccionoFemenino) {
                txt_sexo.setText("F")
            }
        }
    }

    fun obtnerFechaActual() {
        val calendario = Calendar.getInstance()
        dia = calendario.get(Calendar.DAY_OF_MONTH)
        mes = calendario.get(Calendar.MONTH)
        anio = calendario.get(Calendar.YEAR)
    }

    fun mostrarDatePicker() {
        btn_calendario.setOnClickListener {
            obtnerFechaActual()
            val datePicker = DatePickerDialog(
                this,
                this,
                anio,
                mes,
                dia
            )
            datePicker.show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        txt_fecha_nacimiento.setText("${dayOfMonth}/${month}/${year}")
    }

    fun transformarDataString(fecha: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = fecha
        return "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)}/${calendar.get(
            Calendar.YEAR
        )}"
    }

    fun mostrarToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}
