import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException
import java.io.InputStream


fun main(args: Array<String>) {

    val estudiante = Estudiante("Vanessa", Estudiante.ESTADO_CIVIL.soltero, false)
    val sale: List<Universida> = listOf(
        Universida("Salesiana", "1955", mutableListOf(estudiante))
    );

    val datos = leerArchivo()
    val indices = buscarYRetornarIndices("Edwin", datos)
    println(indices)
//    val resultado = buscarEstudiante("estadoCivil", Estudiante.ESTADO_CIVIL.soltero, datos)
//    resultado.forEach { list ->
//        val universidad: Universida = list?.get(0) as Universida
//        val arregloEstudiantes: List<Estudiante> = list?.get(1) as List<Estudiante>
//        val nombresEstudiantes = arregloEstudiantes.map { estudiante: Estudiante ->
//            val arregloDatos = mutableMapOf<String, Any>();
//            arregloDatos.put("Nombre", estudiante.nombre)
//            arregloDatos.put("Estado Civil", estudiante.estadoCivil)
//            return@map arregloDatos
//        }
//        println("Universidad: ${universidad.nombre}")
//        println("Estudiantes coinciden: ${nombresEstudiantes}")
//        println("----------------------------------------------------------------------------------------")
//    }
//    resultado.forEach {
//        universida: Universida? ->
//        println("-------------------------------")
//        println("Nombre: ${universida?.nombre}")
//        println("Fundacion: ${universida?.fundacion}")
//    }
}



