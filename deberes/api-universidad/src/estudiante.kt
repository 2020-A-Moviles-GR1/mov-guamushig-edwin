import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.swing.JOptionPane

class Estudiante(
        var nombre: String,
        var codigo: String,
        var sexo: Char,
        var fechaRegistro: LocalDateTime,
        var tieneBeca: Boolean = false
) {
    enum class ESTADO_CIVIL {
        soltero,
        casado,
        viudo,
        divorsiado
    }
}

fun buscarEstudiante(
        campo: String,
        consulta: String,
        universidades: MutableList<Universida>
): List<List<Any>?> {
    var universidadesEncontrados: List<List<Any>?> = emptyList()
    when (campo) {
        "nombre" -> {
            universidadesEncontrados = universidades
                    .map { universidad: Universida ->
                        val universidaConEstudiante = universidad.estudiantes?.filter { estudiante: Estudiante ->
                            return@filter estudiante.nombre == consulta
                        }
                        return@map universidaConEstudiante?.let { listOf<Any>(universidad, it) }
                    }.filter { list: List<Any>? ->
                        return@filter list != null
                    }.filter { list ->
                        val estudiantesFiltrados = list?.get(1) as List<Estudiante>
                        return@filter estudiantesFiltrados.size > 0
                    }
        }
        "codigo" -> {
            universidadesEncontrados = universidades
                    .map { universidad: Universida ->
                        val universidaConEstudiante = universidad.estudiantes?.filter { estudiante: Estudiante ->
                            return@filter estudiante.codigo == consulta
                        }
                        return@map universidaConEstudiante?.let { listOf<Any>(universidad, it) }
                    }.filter { list: List<Any>? ->
                        return@filter list != null
                    }.filter { list ->
                        val estudiantesFiltrados = list?.get(1) as List<Estudiante>
                        return@filter estudiantesFiltrados.size > 0
                    }
        }
        "sexo" -> {
            universidadesEncontrados = universidades
                    .map { universidad: Universida ->
                        val universidaConEstudiante = universidad.estudiantes?.filter { estudiante: Estudiante ->
                            return@filter estudiante.sexo == consulta.single()
                        }
                        return@map universidaConEstudiante?.let { listOf<Any>(universidad, it) }
                    }.filter { list: List<Any>? ->
                        return@filter list != null
                    }.filter { list ->
                        val estudiantesFiltrados = list?.get(1) as List<Estudiante>
                        return@filter estudiantesFiltrados.size > 0
                    }
        }
//        "fechaRegistro" -> {
//            val fecha = LocalDate.parse(consulta, DateTimeFormatter.ISO_DATE)
//            universidadesEncontrados = universidades
//                .map { universidad: Universida ->
//                    val universidaConEstudiante = universidad.estudiantes?.filter { estudiante: Estudiante ->
//                        return@filter estudiante.fechaRegistro == fecha
//                    }
//                    return@map universidaConEstudiante?.let { listOf<Any>(universidad, it) }
//                }.filter { list: List<Any>? ->
//                    return@filter list != null
//                }.filter { list ->
//                    val estudiantesFiltrados = list?.get(1) as List<Estudiante>
//                    return@filter estudiantesFiltrados.size > 0
//                }
//        }
        "tieneBeca" -> {
            universidadesEncontrados = universidades
                    .map { universidad: Universida ->
                        val universidaConEstudiante = universidad.estudiantes?.filter { estudiante: Estudiante ->
                            return@filter estudiante.tieneBeca == consulta.toBoolean()
                        }
                        return@map universidaConEstudiante?.let { listOf<Any>(universidad, it) }
                    }.filter { list: List<Any>? ->
                        return@filter list != null
                    }.filter { list ->
                        val estudiantesFiltrados = list?.get(1) as List<Estudiante>
                        return@filter estudiantesFiltrados.size > 0
                    }
        }
        else -> {
            JOptionPane.showMessageDialog(null, "Campo ${campo} no encontrado")
//            println("Campo ${campo} no encontrado")
        }
    }
    return universidadesEncontrados
}

fun editarEstudiante(
        codigo: String,
        campoAEditar: String,
        nuevoValor: String,
        universidades: MutableList<Universida>
): MutableList<Universida> {
    val indices = buscarYRetornarIndices(codigo, universidades)
    val existeUniverdad = indices["universidad"]!! > -1
    if (existeUniverdad) {
        val indiceUniversidad = indices["universidad"] as Int
        val indiceEstudiante = indices["estudiante"] as Int
        when (campoAEditar) {
            "nombre" -> {
                universidades[indiceUniversidad].estudiantes?.get(indiceEstudiante)?.nombre = nuevoValor
            }
            "sexo" -> {
                universidades[indiceUniversidad].estudiantes?.get(indiceEstudiante)?.sexo = nuevoValor.single()
            }
            "tieneBeca" -> {
                universidades[indiceUniversidad].estudiantes?.get(indiceEstudiante)?.tieneBeca = nuevoValor.toBoolean()
            }
        }

    }
    return universidades
}


fun crearEstudiante(
        nombreUniversidad: String,
        estudiante: Estudiante,
        universidades: MutableList<Universida>
): MutableList<Universida> {
    val indiceUniversida = buscarYRetornarIndice(nombreUniversidad, universidades)
    val existeUniversidad = indiceUniversida > -1
    if (existeUniversidad) {
        universidades[indiceUniversida].estudiantes?.add(estudiante)
    }
    return universidades
}


fun eliminarEstudiante(
        codigo: String,
        universidades: MutableList<Universida>
): MutableList<Universida> {
    val indices = buscarYRetornarIndices(codigo, universidades)
    val existeUniverdad = indices["universidad"]!! > -1
    val existeEstudiante = indices["estudiante"]!! > -1

    if (existeUniverdad && existeEstudiante) {
        val indiceUniversidad = indices["universidad"] as Int
        val indiceEstudiante = indices["estudiante"] as Int
        universidades[indiceUniversidad].estudiantes?.removeAt(indiceEstudiante)
    }
    return universidades
}

fun buscarYRetornarIndices(
        codigo: String,
        universidades: MutableList<Universida>
): Map<String, Int?> {
    val respuesta = universidades
            .map { universidad: Universida ->
                val universidaConEstudiante = universidad.estudiantes?.filter { estudiante: Estudiante ->
                    return@filter estudiante.codigo == codigo
                }
                return@map universidaConEstudiante?.let { listOf<Any>(universidad, it) }
            }.filter { list: List<Any>? ->
                return@filter list != null
            }.filter { list ->
                val estudiantesFiltrados = list?.get(1) as List<Estudiante>
                return@filter estudiantesFiltrados.size > 0
            }

    val encontroRespuesta = respuesta.size > 0
    if (!encontroRespuesta) {
        JOptionPane.showMessageDialog(null, "No se encontro estudiante: ${codigo}")
//        println("No se encontro estudiante: ${codigo}")
        return mapOf<String, Int?>("universidad" to -1, "estudiante" to -1)
    }
    val indiceUniversidad = universidades.indexOf(respuesta[0]?.get(0))
    val estudiante = respuesta[0]?.get(1) as List<Estudiante>
    val indiceEstudainte = universidades[indiceUniversidad].estudiantes?.indexOf(estudiante[0])
    val indices = mapOf<String, Int?>("universidad" to indiceUniversidad, "estudiante" to indiceEstudainte)
    return indices
}