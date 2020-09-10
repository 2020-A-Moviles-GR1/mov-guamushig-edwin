package com.example.api_universidad

class Universidad(
    var id: Int,
    var nombre: String,
    var fundacion: Int? = null,
    var categoria: String? = null,
    var areaConstruccion: Float? = null,
    var estado: Int? = 1
) {
    override fun toString(): String {
        return "${this.nombre}"
    }
}