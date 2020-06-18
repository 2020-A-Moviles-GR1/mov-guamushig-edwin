import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
//    varibles()
//    sentencias(10)
//    println(sentenciaWhen(750.00))
//    println(sentenciaWhen(tasa = 2.00, sueldo = 300.00));
//    println(calcularSueldo(485.00, 11.5))
    arreglos()
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
    val arrayConstante: Array<String> = arrayOf("a", "b", "c")
    val arrayVariable: ArrayList<String> = arrayListOf<String>()

    arrayVariable.add("Edwin");
    arrayVariable.add("Paul");
    arrayVariable.add("Vanessa");
    arrayVariable.add("Teodoro")

    // recorrer arreglos
    arrayVariable.forEach {
//        println("Valor dentro del arreglo ${it}")
    }
    arrayVariable.forEach { valor ->
//        println("Recorrer de otra forma ${valor}")
    }

    val arregloNumeros = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    // operadores de arreglos
    arregloNumeros.forEach { valor ->
//        println("Valor de iteracion ${valor}")
    }

    val modificado = arregloNumeros.map { valor ->
        val nuevo = valor * 5
        return@map nuevo
    }
    println(modificado)

    val negataivos = arregloNumeros.map { it * -1 }
    println(negataivos)

    val filtrado = arregloNumeros.filter { valor ->
        val nuevo = valor > 2
        return@filter nuevo
    }
    println(filtrado)

    val reducido = arregloNumeros.reduce { acumulador, elemento ->
        acumulador + elemento
    }
    println(reducido)

    val fold = arregloNumeros.fold(3, { acumulador, elemento ->
        acumulador + elemento
    })
    println(fold)

    arrayVariable.addAll(listOf("Pedro", "Maria", "Paquita"));
    val indice = arrayVariable.indexOf("Maria");
    arrayVariable[indice] = "Camilitaa";
    arrayVariable.removeAt(indice)
    val numbers = listOf(7, 4, 9, 6, 3, 1, 0)
    val res = numbers.map { it * it }
    val ordenado = numbers.sorted()
    val encontre = numbers.find { it == 14 }

    val encontreTF = numbers.any { it ->
        val menor = it > 5
        return@any menor
    }
    println(encontreTF)

    val encontreT = numbers.all { it ->
        val menor = it > 5
        return@all menor
    }
    println(encontreT)

    println(ordenado)
    println(encontre)
}

fun bucles() {
    var myArray: List<Int> = arrayListOf(1, 2, 3, 4, 5, 6);
    var myMap: MutableMap<String, Int> = mutableMapOf("Juan" to 2, "Emilio" to 56)
    println(myArray)
    println(myMap)

    for (myString in myArray) {
        println(myString)
    }

    for (element in myMap) {
        println("${element.key} es la llave y ${element.value} es el valor")
    }

    for (x in 0 until 5) {
        print("${x} - ")
    }

    for (x in 0 until 10 step 2) {
        print("${x} - ")
    }
    println("\n")
    for (x in 10 downTo 0) {
        print("${x} - ")
    }
}

fun valoresNull() {
    var nulo: String? = "cosaaa";
    println(nulo?.length)
    nulo = null;
    println(nulo?.length) // !! obliga que no sea nula

    var stringsito: String? = "Este es un mesanje";
    stringsito?.let {
        println(it)
    } ?: run {
        println("Fueraa")
    }
}
