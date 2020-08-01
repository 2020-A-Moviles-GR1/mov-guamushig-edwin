package com.example.api_universidad

import android.util.Log
import kotlin.collections.ArrayList

class ServicioBDD {
    companion object {
        var universidades = ArrayList<Universidad>()

        fun aniadirUniversidad(universidad: Universidad) {
            this.universidades.add(universidad)
        }

        fun actualizarUniversidad(idUniversidad: Int, universidadActualizada: Universidad) {
            this.universidades[idUniversidad] = universidadActualizada
        }

        fun eliminarUniversidad(idUniversidad: Int) {
            this.universidades.removeAt(idUniversidad)
        }


        var estudiantes = ArrayList<Estudiante>()

        fun retornarEstudiantesPorUniversidad(idUniversidad: Int): ArrayList<Estudiante> {
            val lista = this.estudiantes.filter { estudiante: Estudiante ->
                return@filter estudiante.universidad == idUniversidad
            }
            return ArrayList(lista)
        }

        fun aniadirEstudiante(estudiante: Estudiante) {
            this.estudiantes.add(estudiante)
        }

        fun actualizarEstudiante(idEstudiante: Int, estudianteActualizado: Estudiante) {
            this.estudiantes[idEstudiante] = estudianteActualizado
        }

        fun eliminarEstudiante(idEstudiante: Int) {
            this.estudiantes.removeAt(idEstudiante)
        }
    }
}