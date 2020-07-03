import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList


fun main(args: Array<String>) {
    val gson = Gson()
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()

//
    val estudianteT = Estudiante("Vanessa", Estudiante.ESTADO_CIVIL.soltero, false)
    val sale: List<Universida> = listOf(
        Universida("Salesiana", "1955", listOf(estudianteT))
    );

//    val estudianteX = Estudiante("Katherine", Estudiante.ESTADO_CIVIL.soltero, false)
//    val cato: List<Universida> = listOf(
//        Universida("Catolica", "1955", listOf(estudianteX))
//    );
//
//    val universida = poli + cato + sale
//
//    val jsonTutsList: String = gson.toJson(universida)
//
//    val jsonTutsListPretty: String = gsonPretty.toJson(universida)
//    escribirEnArchivo(jsonTutsListPretty)
    val jsonList = leerArchivo()

    val mutableListTutorialType = object : TypeToken<MutableList<Universida>>() {}.type
    var datos: MutableList<Universida> = gson.fromJson(jsonList, mutableListTutorialType)

    val respuestita = datos.forEach {
        universida: Universida ->
        val some = universida.estudiantes
        print(estudianteT)
        print(universida.estudiantes)
        print(some)
    }
}

fun escribirEnArchivo(dato: String) {
    try {
        File("db.txt").printWriter().use { out ->
            out.println(dato)
        }
        File("db.json").printWriter().use { out ->
            out.println(dato)
        }
    } catch (e: IOException) {
        print(e)
    }
}

fun leerArchivo(): String {
    val inputStream: InputStream = File("db.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
//    val mapper = jacksonObjectMapper()
//    mapper.registerKotlinModule()
//    mapper.registerModule(JavaTimeModule())
//
//    val jsonString: String = File("./src/main/resources/db.json").readText(Charsets.UTF_8)
//    val jsonTextList: List<Universida> = mapper.readValue<List<Universida>>(jsonString)
}
