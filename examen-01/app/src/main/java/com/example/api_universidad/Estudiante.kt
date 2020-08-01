package com.example.api_universidad

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Estudiante(
    var nombre: String,
    var fechaNacimiento: Date,
    var sexo: Char,
    var estatura: Float,
    var tieneBeca: Boolean,
    var universidad: Int
) {
    override fun toString(): String {
        return "${nombre} - ${calcularEdad(fechaNacimiento)}"
    }

    fun calcularEdad(fechaNac: Date): String {
        val fechaActual: Date = Date()
        val fechaFormateada: DateFormat = SimpleDateFormat("yyyyMMdd")
        val fechaUno: Int = fechaFormateada.format(fechaActual).toInt()
        val fechaDos: Int = fechaFormateada.format(fechaNac).toInt()
        val edad = (fechaUno - fechaDos) / 10000
        return "Edad: ${edad}"
    }
}