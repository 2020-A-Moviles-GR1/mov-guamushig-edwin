package com.example.api_universidad

class Universidad(
    var nombre: String,
    var fundacion: Int? = null,
    var categoria: Char? = null,
    var areaConstruccion: Float? = null,
    var estado: Boolean? = true
) {
    override fun toString(): String {
        return "${this.nombre}"
    }
}