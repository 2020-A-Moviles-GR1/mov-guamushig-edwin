package com.example.myfirstapplication

class Entrenador(
    var nombre: String,
    var apellido: String
) {
    override fun toString(): String {
        // return super.toString()
        return "${this.nombre} ${this.apellido}"
    }
}