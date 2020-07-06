class Estudiante(
    var nombre: String,
    var estadoCivil: ESTADO_CIVIL,
    var tieneEnfermedad: Boolean
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
    consulta: Any,
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
        "estadoCivil" -> {
            universidadesEncontrados = universidades
                .map { universidad: Universida ->
                    val universidaConEstudiante = universidad.estudiantes?.filter { estudiante: Estudiante ->
                        return@filter estudiante.estadoCivil == consulta
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
            println("Campo ${campo} no encontrado")
        }
    }
    return universidadesEncontrados
}

fun editarEstudiante(
    nombre: String,
    campoAEditar: String,
    nuevoValor: Any,
    universidades: MutableList<Universida>
): MutableList<Universida> {
    val indices = buscarYRetornarIndices(nombre, universidades)
    val existeUniverdad = indices["universidad"]!! > -1
    if (existeUniverdad) {
        val indiceUniversidad = indices["universidad"] as Int
        val indiceEstudiante = indices["estudiante"] as Int
        when (campoAEditar) {
            "nombre" -> {
                universidades[indiceUniversidad].estudiantes?.get(indiceEstudiante)?.nombre = nuevoValor as String
            }
            "estadoCivil" -> {
                universidades[indiceUniversidad].estudiantes?.get(indiceEstudiante)?.estadoCivil =
                    nuevoValor as Estudiante.ESTADO_CIVIL
            }
        }

    }
    return universidades
}


fun crearEstudiante(
    nombreUniversidad: String,
    universidades: MutableList<Universida>
): MutableList<Universida> {
    val indiceUniversida = buscarYRetornarIndice(nombreUniversidad, universidades)
    val existeUniversidad = indiceUniversida > -1
    if (existeUniversidad) {
        val estudiante = Estudiante("Juanjo", Estudiante.ESTADO_CIVIL.viudo, true)
        universidades[indiceUniversida].estudiantes?.add(estudiante)
    }
    return universidades
}


fun eliminarEstudiante(nombre: String, universidades: MutableList<Universida>): MutableList<Universida> {
    val indices = buscarYRetornarIndices(nombre, universidades)
    val existeUniverdad = indices["universidad"]!! > -1
    val existeEstudiante = indices["estudiante"]!! > -1

    if (existeUniverdad && existeEstudiante) {
        val indiceUniversidad = indices["universidad"] as Int
        val indiceEstudiante = indices["estudiante"] as Int
        universidades[indiceUniversidad].estudiantes?.removeAt(indiceEstudiante)
    }
    return universidades
}

fun buscarYRetornarIndices(nombre: String, universidades: MutableList<Universida>): Map<String, Int?> {
    val respuesta = universidades
        .map { universidad: Universida ->
            val universidaConEstudiante = universidad.estudiantes?.filter { estudiante: Estudiante ->
                return@filter estudiante.nombre == nombre
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
        println("No se encontro estudiante: $nombre")
        return mapOf<String, Int?>("universidad" to -1, "estudiante" to -1)
    }
    val indiceUniversidad = universidades.indexOf(respuesta[0]?.get(0))
    val estudiante = respuesta[0]?.get(1) as List<Estudiante>
    val indiceEstudainte = universidades[indiceUniversidad].estudiantes?.indexOf(estudiante[0])
    val indices = mapOf<String, Int?>("universidad" to indiceUniversidad, "estudiante" to indiceEstudainte)
    return indices
}