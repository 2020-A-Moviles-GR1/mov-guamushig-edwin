import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
//    varibles()
//    sentencias(10)
//    println(sentenciaWhen(750.00))
//    println(sentenciaWhen(tasa = 2.00, sueldo = 300.00));
//    println(calcularSueldo(485.00, 11.5))
//    arreglos()
//    val nuevoNumeroUno = sumarDosNumeros(5, 8);
//    val nuevoNumeroDos = sumarDosNumeros(null, 8);
//    val nuevoNumeroTres = sumarDosNumeros(5, null);
//    val nuevoNumeroCuatro = sumarDosNumeros(null, null);

//    println(sumarDosNumeros.arregloNumeros)
//    sumarDosNumeros.agregarNumero(69)
//    println(sumarDosNumeros.arregloNumeros)
//    sumarDosNumeros.eliminarNumero(0)
//    println(sumarDosNumeros.arregloNumeros)

    var nombre: String? = null;
    nombre = "Edwin"
    mostrarNombre(nombre)

}

fun mostrarNombre(nombre: String?) {
    println("Longitud de nombre es ${nombre?.length}")
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

abstract class Numeros( // val nuevosNumeros = Numeros(1,2)
    protected var numeroUno: Int,
    protected var numeroDos: Int
) {
}

class Suma(
    uno: Int,
    dos: Int
) : Numeros(uno, dos) {
    fun sumar(): Int {
        // this.uno
        return this.numeroUno + this.numeroDos
    }
}

class sumarDosNumeros(uno: Int, dos: Int) : Numeros(uno, dos) {
    constructor(uno: Int?, dos: Int) : this(if (uno == null) 0 else uno, dos) {
        val numUno = if (uno == null) 0 else uno
        this.numeroUno = numUno
        this.numeroDos = dos
        println("Constructor 1")
    }

    constructor(uno: Int, dos: Int?) : this(uno, if (dos == null) 0 else dos) {
        val numDos = if (dos == null) 0 else dos
        this.numeroUno = uno
        this.numeroDos = numDos
        println("Constructor 2")

    }

    constructor(uno: Int?, dos: Int?) : this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    ) {
        val numDos = if (dos == null) 0 else dos
        val numUno = if (uno == null) 0 else uno
        this.numeroUno = numUno
        this.numeroDos = numDos
        println("Constructor 3")

    }
//
//    init {
//        println("Funcion init")
//    }

    companion object {
        val arregloNumeros = arrayListOf<Int>(1, 2, 3, 4, 5)

        fun agregarNumero(nuevoNumero: Int) {
            this.arregloNumeros.add(nuevoNumero)
        }

        fun eliminarNumero(posicion: Int) {
            this.arregloNumeros.removeAt(posicion)
        }
    }
}