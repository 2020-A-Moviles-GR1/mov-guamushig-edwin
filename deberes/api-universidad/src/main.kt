import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.IOException
import java.util.*

fun main(args: Array<String>) {
    val gson = Gson()
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()

    val estudiante = Estudiante("Edwin", Estudiante.ESTADO_CIVIL.soltero, false)
    val estudianteD = Estudiante("Edwin", Estudiante.ESTADO_CIVIL.soltero, false)
    val estudianteT = Estudiante("Paul", Estudiante.ESTADO_CIVIL.soltero, false)

    val tutsList: List<Universida> = listOf(
        Universida("Politecnica", Date(), listOf(estudiante, estudianteD, estudianteT))
    );
    val tutsList2: List<Universida> = listOf(
        Universida("Politecnicass", Date(), listOf(estudiante, estudianteD, estudianteT))
    );

    val universida= tutsList + tutsList2

    val jsonTutsList: String = gson.toJson(universida)

    val jsonTutsListPretty: String = gsonPretty.toJson(universida)
    println(jsonTutsListPretty)
    escribirEnArchivo(jsonTutsListPretty)
}

fun escribirEnArchivo(dato: String) {
    try {
        File("db.json").printWriter().use { out ->
            out.println(dato)
        }
    } catch (e: IOException) {
        print(e)
    }
}

fun leerArchivo(): String {
    return "hola"
}
