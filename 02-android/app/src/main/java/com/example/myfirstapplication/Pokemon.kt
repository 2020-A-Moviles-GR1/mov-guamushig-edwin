package com.example.myfirstapplication

import android.util.Log
import com.beust.klaxon.*
import java.util.*
import kotlin.collections.ArrayList

class Pokemon(
    var createdAt: Long,
    var updatedAt: Long,
    var id: Int,
    var nombre: String,
    var usuario: Any
) {
    var fechaCreacion: Date
    var fechaActualizacion: Date

    init {
        fechaCreacion = Date(createdAt)
        fechaActualizacion = Date(updatedAt)
    }

    companion object {
        val myConverter = object : Converter {

            override fun canConvert(clase: Class<*>) = clase == Pokemon::class.java

            override fun fromJson(jsonValue: JsonValue): Pokemon? {
                return when (jsonValue.obj?.get("usuario")) {
                    is Int -> Pokemon(
                        jsonValue.objInt("createdAt").toLong(),
                        jsonValue.objInt("updatedAt").toLong(),
                        jsonValue.objInt("id"),
                        jsonValue.objString("nombre"),
                        jsonValue.objInt("usuario")
                    )
                    else -> Klaxon().parseFromJsonObject<UsuarioHttp>(
                        jsonValue.obj?.get("usuario") as JsonObject
                    )?.let {
                        Pokemon(
                            jsonValue.objInt("createdAt").toLong(),
                            jsonValue.objInt("updatedAt").toLong(),
                            jsonValue.objInt("id"),
                            jsonValue.objString("nombre"),
                            it
                        )
                    }
                }
            }

            override fun toJson(value: Any): String {
                TODO("Not yet implemented")
            }

        }
    }

}