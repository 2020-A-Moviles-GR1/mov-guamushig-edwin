package com.example.api_universidad

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonObject
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut

class ServicioBDD {
    companion object {

        val url = "http://192.168.100.10:1337"

        fun findAll(segmento: String) = "$url/$segmento"
            .httpGet()
            .responseString { request, response, result ->
            }

        fun findById(segmento: String, id: Int) = "$url/$segmento/$id".httpGet()

        fun createOne(segmento: String, nuevoDato: MutableList<Pair<String, Any>>) =
            "$url/$segmento".httpPost(nuevoDato)

        fun updateOne(segmento: String, id: Int, datoActualizado: List<Pair<String, Any>>) =
            "$url/$segmento/$id".httpPut(datoActualizado)

        fun deleteOne(segmento: String, id: Int) = "$url/$segmento/$id".httpDelete()

        fun findStudentsByUniversity(idUniversidad: Int) =
            "$url/estudiante?universidad=$idUniversidad".httpGet()


        val convertirUniversidad = object : Converter {

            override fun canConvert(clase: Class<*>) = clase == Universidad::class.java

            override fun fromJson(jsonValue: JsonValue): Universidad {
                return Universidad(
                    jsonValue.objInt("id"),
                    jsonValue.objString("nombre"),
                    jsonValue.objInt("fundacion"),
                    jsonValue.objString("categoria"),
                    jsonValue.objInt("areaConstruccion").toFloat(),
                    jsonValue.objInt("estado")
                )
            }

            override fun toJson(value: Any): String {
                TODO("Not yet implemented")
            }
        }

        val convertirEstudiante = object : Converter {

            override fun canConvert(clase: Class<*>) = clase == Estudiante::class.java

            override fun fromJson(jsonValue: JsonValue): Estudiante? {
                return Klaxon().parseFromJsonObject<Universidad>(
                    jsonValue.obj?.get("universidad") as JsonObject
                )?.let {
                    Estudiante(
                        jsonValue.objInt("id"),
                        jsonValue.objString("nombre"),
                        jsonValue.objString("fechaNacimiento"),
                        jsonValue.objString("sexo"),
                        jsonValue.objInt("estatura").toDouble(),
                        jsonValue.objInt("tieneBeca"),
                        jsonValue.objString("latitud").toDouble(),
                        jsonValue.objString("longitud").toDouble(),
                        jsonValue.objString("urlImagen"),
                        jsonValue.objString("urlRedSocial"),
                        it
                    )
                }
            }

            override fun toJson(value: Any): String {
                TODO("Not yet implemented")
            }
        }
    }
}