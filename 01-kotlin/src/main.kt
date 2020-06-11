import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    varibles()
    sentencias(10)
    println(sentenciaWhen(750.00))
    println(sentenciaWhen(tasa = 2.00, sueldo = 300.00));
    println(calcularSueldo(485.00, 11.5))
}

fun varibles() {
    // Mutables
    var numeroJugadores = 11
    var numeroSuplentes = 4
    numeroJugadores = 9
    numeroSuplentes = 6

    // Inmutables: No se pueden reasignar
    val edad: Int = 23
    val nombre: String = "Edwin"
    val char: Char = 'a'
    val decimal: Double = 2.3
    val fechaNac: Date = Date();
    val decimalDos: Float = 13.5F
    val habilitado: Boolean = true
    val condicion = habilitado === false
}

fun sentencias(condicion: Int) {
    val miNumero = 5;
    val esMenor = miNumero > condicion;
    if (esMenor) {
        println("Es menor")
    } else {
        println("Es mayor")
    }
}

fun sentenciaWhen(sueldo: Double, tasa: Double = 11.5): Boolean {
    when (sueldo) {
        in 0.0..200.00 -> {
            println("Sueldo bajo")
        }
        in 200.001..500.00 -> {
            println("Sueldo normal")
        }
        in 501.00..999.99 -> {
            println("Sueldo alto")
        }
        else -> {
            println("Ganas mas de lo normal")
        }
    }

    val esSueldoMayor = if (sueldo > 500) true else false;
    return esSueldoMayor;
}

fun calcularSueldo(sueldo: Double, tasa: Double = 8.5, esEspecial: Int? = null): Double {
    if (esEspecial === null) {
        return sueldo * tasa;
    } else {
        return sueldo * tasa * esEspecial
    }
}


fun arreglos() {

    val myArray: ArrayList<String> = arrayListOf<String>()
    myArray.add("Edwin");
    myArray.add("Paul");
    myArray.add("Vanessa");
    myArray.add("Teodoro")

    myArray.addAll(listOf("Pedro", "Maria", "Paquita"));

    println(myArray)

    val indice = myArray.indexOf("Maria");
    myArray[indice] = "Camilitaa";
    println(myArray)

    myArray.removeAt(indice)
    println(myArray)

    val numbers = listOf(1, 2, 3)
    val res = numbers.map { it * it }
    println(res)
}

