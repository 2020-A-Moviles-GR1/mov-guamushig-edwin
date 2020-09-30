package `manejo-menu`

import Universida
import `manejo-archivo`.escribirEnArchivo
import `manejo-archivo`.leerArchivo
import buscarUniversidad
import crearUniversidad
import editarUniversidad
import eliminarUniversidad
import listarTodo
import javax.swing.JOptionPane

fun stringMenuInicio(): String {
    return "     BIENVENIDO AL SISTEMA DE UNIVERSIDADES      \n\n" +
            "Seleccione las opciones:\n" +
            "1. Crear universidad\n" +
            "2. Actualizar universidad\n" +
            "3. Eliminar universidad\n" +
            "4. Buscar universidad por atributo\n" +
            "5. Listar todas las universidad\n" +
            "6. Gestionar estudiantes\n" +
            "0. Salir\n"
}

fun crearUniversidadMenu() {
    val nombre = JOptionPane.showInputDialog("Ingrese nombre de universidad")
    val fundacion = JOptionPane.showInputDialog("Ingrese el anio de fundacion")
    val direccion = JOptionPane.showInputDialog("Ingrese la direccion")
    val datos = leerArchivo()
    val universidadNueva = crearUniversidad(nombre, fundacion.toInt(), direccion)
    val nuevo = datos + universidadNueva
    escribirEnArchivo(nuevo)
}

fun editarUniversidadMenu() {
    val nombre = JOptionPane.showInputDialog("Ingrese nombre de universidad a editar")
    val campo = JOptionPane.showInputDialog("Ingrese campo de universidad a editar")
    val nuevoValor = JOptionPane.showInputDialog("Ingrese el nuevo valor")
    val datos = leerArchivo()
    val universidadEditada = editarUniversidad(nombre, campo, nuevoValor, datos)
    escribirEnArchivo(universidadEditada)
}

fun eliminarUniversidadMenu() {
    val nombre = JOptionPane.showInputDialog("Ingrese el nombre de la universidad a eliminar")
    val datos = leerArchivo()
    val universidadEditada = eliminarUniversidad(nombre, datos)
    escribirEnArchivo(universidadEditada)
}

fun buscarUniversidaMenu() {
    val campo = JOptionPane.showInputDialog("Ingrese campo por el que desea buscar")
    val consulta = JOptionPane.showInputDialog("Ingrese su busqueda")
    val datos = leerArchivo()
    val universidadEncontrada = buscarUniversidad(campo, consulta, datos)
    val existe = universidadEncontrada.size > 0
    if (existe) {
        var menuRespuesta = ""
        universidadEncontrada.forEach { universida: Universida? ->
            menuRespuesta += "-------------------------------------------------\n" +
                    "Nombre: ${universida?.nombre}\n" +
                    "Fundacion: ${universida?.fundacion}\n" +
                    "Numero de estudiantes: ${universida?.estudiantes?.size}\n"
        }
        menuRespuesta += "-------------------------------------------------\n"
        JOptionPane.showMessageDialog(null, menuRespuesta)
    } else {
        JOptionPane.showMessageDialog(null, "Universidad no encontrada")
    }
}

fun listarTodoMenu() {
    val total = listarTodo()
    JOptionPane.showMessageDialog(null, total)
}