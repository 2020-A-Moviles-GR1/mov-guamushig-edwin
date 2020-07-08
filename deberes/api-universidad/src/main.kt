import `manejo-archivo`.escribirEnArchivo
import `manejo-archivo`.leerArchivo
import `manejo-menu`.*
import javax.swing.JOptionPane

fun main(args: Array<String>) {
    menu()
}


fun menu() {
    val menuInicio = stringMenuInicio()
    do {
        val entradaTexto = JOptionPane.showInputDialog(menuInicio)
        val opcion = Integer.parseInt(entradaTexto)
        when (opcion) {
            1 -> {
                crearUniversidadMenu()
            }
            2 -> {
                editarUniversidadMenu()
            }
            3 -> {
                eliminarUniversidadMenu()
            }
            4 -> {
                buscarUniversidaMenu()
            }
            5 -> {
                listarTodoMenu()
            }
            6 -> {
                menuEstudiantil()
            }
            else -> {
                println("Opcion seleccionada no existe")
            }
        }
    } while (opcion != 0)
}

fun menuEstudiantil() {
    val menuInicioEstudiante = stringMenuInicioEstudiante()
    do {
        val entradaTexto = JOptionPane.showInputDialog(menuInicioEstudiante)
        val opcion = Integer.parseInt(entradaTexto)
        when (opcion) {
            1 -> {
                crearEstudianteMenu()
            }
            2 -> {
                editarEstudianteMenu()
            }
            3 -> {
                eliminarEstudianteMenu()
            }
            4 -> {
                buscarEstudiantePorAtributoMenu()
            }
            5 -> {
                crearEstudianteMenu()
            }
            6 -> {
                menu()
            }
            7 -> {
                listarTodoMenu()
            }
            else -> {
                println("Opcion seleccionada no existe")
            }
        }
    } while (opcion == 0)
}
