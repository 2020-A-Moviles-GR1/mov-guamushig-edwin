package com.example.api_universidad

import java.time.LocalDate
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
        return "${nombre} [${tieneBeca}]"
    }
}