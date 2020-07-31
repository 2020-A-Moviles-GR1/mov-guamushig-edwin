package com.example.api_universidad

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_crear_editar_estudiante.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

    fun mostrarToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}
