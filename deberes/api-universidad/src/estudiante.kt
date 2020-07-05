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

//fun editarUniversidad(
//    nombre: String,
//    campoAEditar: String,
//    nuevoValor: Any,
//    universidades: MutableList<Universida>
//): MutableList<Universida> {
//    val indice = buscarYRetornarIndice(nombre, universidades)
//    val existeUniverdad = indice > -1
//    if (existeUniverdad) {
//        when (campoAEditar) {
//            "nombre" -> {
//                universidades[indice].nombre = nuevoValor as String
//            }
//            "fundacion" -> {
//                universidades[indice].fundacion = nuevoValor as String
//            }
//        }
//    }
//    return universidades
//}
//
//fun crearUniversidad(): MutableList<Universida> {
//    return mutableListOf(Universida("ESPOCH", "1945"))
//}
//
//
//fun eliminarUniversidad(nombre: String, universidades: MutableList<Universida>): MutableList<Universida> {
//    val indice = buscarYRetornarIndice(nombre, universidades)
//    val existeUniverdad = indice > -1
//    if (existeUniverdad) {
//        universidades.removeAt(indice)
//    }
//    return universidades
//}
//
//fun buscarYRetornarIndice(nombre: String, universidades: MutableList<Universida>): Int {
//    val respuesta = universidades.filter { universida: Universida ->
//        return@filter universida.nombre == nombre
//    }
//    val existeUniversida = respuesta.size > 0
//    if (!existeUniversida) {
//        println("No se encontro universidad ${nombre}")
//        return -1
//    }
//    return universidades.indexOf(respuesta[0])
//
//}