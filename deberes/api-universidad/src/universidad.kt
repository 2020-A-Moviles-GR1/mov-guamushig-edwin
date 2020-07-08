import com.google.gson.Gson
import java.io.File
import java.io.InputStream
import java.util.*

class Universida(
    var nombre: String,
    var fundacion: String? = null,
    var estudiantes: MutableList<Estudiante>? = null
) {
}

fun buscarUniversidad(
    campo: String,
    consulta: Any,
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
            universidadesEncontradas = universidades
                .filter { universida: Universida ->
                    return@filter universida.fundacion == consulta
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
    nuevoValor: Any,
    universidades: MutableList<Universida>
): MutableList<Universida> {
    val indice = buscarYRetornarIndice(nombre, universidades)
    val existeUniverdad = indice > -1
    if (existeUniverdad) {
        when (campoAEditar) {
            "nombre" -> {
                universidades[indice].nombre = nuevoValor as String
            }
            "fundacion" -> {
                universidades[indice].fundacion = nuevoValor as String
            }
        }
    }
    return universidades
}

fun crearUniversidad(
    nombre: String,
    fundacion: String? = null
): MutableList<Universida> {
    val estudiantes = mutableListOf<Estudiante>()
    return mutableListOf(Universida(nombre, fundacion, estudiantes))
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
