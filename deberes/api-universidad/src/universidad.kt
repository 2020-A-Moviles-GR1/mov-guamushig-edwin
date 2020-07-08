import java.io.File
import java.io.InputStream

class Universida(
    var nombre: String,
    var fundacion: Int,
    var direccion: String,
    var habilitado: Boolean = true,
    var estudiantes: MutableList<Estudiante>? = null
) {
}

fun buscarUniversidad(
    campo: String,
    consulta: String,
    universidades: MutableList<Universida>
): List<Universida> {
    var universidadesEncontradas: List<Universida> = emptyList()
    when (campo) {
        "nombre" -> {
            universidadesEncontradas = universidades
                .filter { universida: Universida ->
                    return@filter universida.nombre == consulta
                }
        }
        "fundacion" -> {
            val consultaInt = consulta.toInt()
            universidadesEncontradas = universidades
                .filter { universida: Universida ->
                    return@filter universida.fundacion == consultaInt
                }
        }
        "direccion" -> {
            universidadesEncontradas = universidades
                .filter { universida: Universida ->
                    return@filter universida.direccion == consulta
                }
        }
        "habilitado" -> {
            val consultaBool = consulta.toBoolean()
            universidadesEncontradas = universidades
                .filter { universida: Universida ->
                    return@filter universida.habilitado == consultaBool
                }
        }
        else -> {
            println("Campo ${campo} no encontrado")
        }
    }
    return universidadesEncontradas
}

fun editarUniversidad(
    nombre: String,
    campoAEditar: String,
    nuevoValor: String,
    universidades: MutableList<Universida>
): MutableList<Universida> {
    val indice = buscarYRetornarIndice(nombre, universidades)
    val existeUniverdad = indice > -1
    if (existeUniverdad) {
        when (campoAEditar) {
            "nombre" -> {
                universidades[indice].nombre = nuevoValor
            }
            "fundacion" -> {
                universidades[indice].fundacion = nuevoValor.toInt()
            }
            "direccion" -> {
                universidades[indice].direccion = nuevoValor
            }
            "habilitado" -> {
                universidades[indice].habilitado = nuevoValor.toBoolean()
            }
        }
    }
    return universidades
}

fun crearUniversidad(
    nombre: String,
    fundacion: Int,
    direccion: String
): MutableList<Universida> {
    val estudiantes = mutableListOf<Estudiante>()
    return mutableListOf(Universida(nombre, fundacion, direccion, estudiantes = estudiantes))
}

fun eliminarUniversidad(
    nombre: String,
    universidades: MutableList<Universida>
): MutableList<Universida> {
    val indice = buscarYRetornarIndice(nombre, universidades)
    val existeUniverdad = indice > -1
    if (existeUniverdad) {
        universidades.removeAt(indice)
    }
    return universidades
}

fun buscarYRetornarIndice(nombre: String, universidades: MutableList<Universida>): Int {
    val respuesta = universidades.filter { universida: Universida ->
        return@filter universida.nombre == nombre
    }
    val existeUniversida = respuesta.size > 0
    if (!existeUniversida) {
        println("No se encontro universidad ${nombre}")
        return -1
    }
    return universidades.indexOf(respuesta[0])

}

fun listarTodo(): String {
    val inputStream: InputStream = File("db.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}
