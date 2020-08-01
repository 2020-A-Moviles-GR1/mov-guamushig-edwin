package com.example.api_universidad

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_crear_editar_estudiante.*
import java.util.*

class CrearEditarEstudianteActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private var idUniversidad: Int = -1
    private var idEstudiante: Int = -1
    private var dia = 0
    private var mes = 0
    private var anio = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_editar_estudiante)

        idUniversidad = intent.getIntExtra("idUniversidad", -1)
        txt_id_universidad.setText("${idUniversidad}")
        idEstudiante = intent.getIntExtra("idEstudiante", -1)

        if (idEstudiante != -1) {
            val estudiante: Estudiante = ServicioBDD.estudiantes[idEstudiante]
            cargarConfiguracionInicial(estudiante)
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
    }

    fun guardar() {

        val idUniversidad = txt_id_universidad.text.toString()
        val nombre = txt_nombre.text.toString()
        val fechaNac = txt_fecha_nacimiento.text.toString()
        val sexo = txt_sexo.text.toString()
        val estatura = txt_estatura.text.toString()
        val tieneBeca = txt_beca.text.toString()

        val estudiante = Estudiante(
            nombre,
            Date(fechaNac),
            sexo.single(),
            estatura.toFloat(),
            tieneBeca.toBoolean(),
            idUniversidad.toInt()
        )

        if (idEstudiante == -1) {
            ServicioBDD.aniadirEstudiante(estudiante)
            mostrarToast("Estudiante ${nombre} creado correctamente")
        } else {
            ServicioBDD.actualizarEstudiante(idEstudiante, estudiante)
            mostrarToast("Estudiante ${nombre} actualizado correctamente")
        }
    }

    @SuppressLint("SetTextI18n")
    fun cargarConfiguracionInicial(estudiante: Estudiante) {
        txt_nombre.setText(estudiante.nombre)
        txt_fecha_nacimiento.setText(transformarDataString(estudiante.fechaNacimiento))
        txt_estatura.setText(estudiante.estatura.toString())
        rd_btn_masculino.isEnabled = false
        rd_btn_femenino.isEnabled = false
        btn_calendario.isEnabled = false
        if (estudiante.sexo == "M".single()) {
            raddio_button_sexo.check(rd_btn_masculino.id)
            txt_sexo.setText("M")
        } else {
            raddio_button_sexo.check(rd_btn_femenino.id)
            txt_sexo.setText("F")
        }

        if (estudiante.tieneBeca) {
            check_beca.setChecked(true)
        }

    }


    fun setearCheckbox() {
        check_beca.setOnClickListener {
            val tieneBeca = check_beca.isChecked()
            txt_beca.setText("${tieneBeca}")
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
        return "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)}/${calendar.get(Calendar.YEAR)}"
    }

    fun mostrarToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}
