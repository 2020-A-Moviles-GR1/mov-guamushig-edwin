package com.example.myfirstapplication

import java.util.*

class Pokemon(
    var createdAt: Long,
    var updatedAt: Long,
    var id: Int,
    var nombre: String,
    var usuario: Int
) {
    var fechaCreacion: Date
    var fechaActualizacion: Date
    init {
        fechaCreacion = Date(createdAt)
        fechaActualizacion = Date(updatedAt)
    }


}