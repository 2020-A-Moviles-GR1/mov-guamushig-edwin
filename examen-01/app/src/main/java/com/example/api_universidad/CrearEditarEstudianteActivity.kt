package com.example.api_universidad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CrearEditarEstudianteActivity : AppCompatActivity() {

    private  var idUniversidad: Int = -1
    private  var idEstudiante: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_editar_estudiante)

        idUniversidad = intent.getIntExtra("idUniversidad", -1)
        idEstudiante = intent.getIntExtra("idEstudiante", -1)

        if(idEstudiante != -1) {
            val estudiante: Estudiante = ServicioBDD.estudiantes[idEstudiante]
        }
    }
}