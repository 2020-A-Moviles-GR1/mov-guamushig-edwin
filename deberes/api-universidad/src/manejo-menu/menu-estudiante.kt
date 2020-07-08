package `manejo-menu`

import Estudiante
import Universida
import `manejo-archivo`.escribirEnArchivo
import `manejo-archivo`.leerArchivo
import buscarEstudiante
import crearEstudiante
import editarEstudiante
import eliminarEstudiante
import javax.swing.JOptionPane


fun stringMenuInicioEstudiante(): String {
    return "      GESTION DE ESTUDIANTES      \n\n" +
            "Seleccione las opciones:\n" +
            "1. Crear estudiante\n" +
            "2. Actualizar estudiante\n" +
            "3. Eliminar estudiante\n" +
            "4. Buscar estudiante por atributo\n" +
            "5. Listar todas las estudiante\n" +
            "6. Volver a gestion universidades\n" +
            "7. Listar todo\n" +
            "0. Volver a menu universidades\n"
}

fun crearEstudianteMenu() {
    val datos = leerArchivo()
    val nombreUniversida = JOptionPane.showInputDialog("Ingrese el nombre de la universidad donde crear")
    val nombre = JOptionPane.showInputDialog("Ingrese el nombre")
    val estadoCivil = JOptionPane.showInputDialog("Ingrese el estado civil")
    val tieneEnfermedad = JOptionPane.showInputDialog("Ingrese true/false si tiene enfermedad")
    val estudiante = Estudiante(nombre, estadoCivil, tieneEnfermedad.toBoolean())
    val estudianteCreado = crearEstudiante(nombreUniversida, estudiante, datos)
    escribirEnArchivo(estudianteCreado)
}

fun editarEstudianteMenu() {
    val datos = leerArchivo()
    val nombre = JOptionPane.showInputDialog("Ingrese el nombre de estudiante a editar")
    val campo = JOptionPane.showInputDialog("Ingrese el campo de estudiante a editar")
    val nuevoValor = JOptionPane.showInputDialog("Ingrese el nuevo de estudiante a editar")
    val respuesta = editarEstudiante(nombre, campo, nuevoValor, datos)
    escribirEnArchivo(respuesta)
}

fun eliminarEstudianteMenu() {
    val datos = leerArchivo()
    val nombre = JOptionPane.showInputDialog("Ingrese el nombre de estudiante a eliminar")
    val respuesta = eliminarEstudiante(nombre, datos)
    escribirEnArchivo(respuesta)
}

fun buscarEstudiantePorAtributoMenu() {
    val datos = leerArchivo()
    val campo = JOptionPane.showInputDialog("Ingrese el campo por el que desea buscar")
    val consulta = JOptionPane.showInputDialog("Ingrese su busqueda")
    val respuesta = buscarEstudiante(campo, consulta, datos)
    var stringRespuesta = ""
    respuesta.forEach { list ->
        val universidad: Universida = list?.get(0) as Universida
        val arregloEstudiantes: List<Estudiante> = list?.get(1) as List<Estudiante>
        val nombresEstudiantes = arregloEstudiantes.map { estudiante: Estudiante ->
            val arregloDatos = mutableMapOf<String, Any>();
            arregloDatos.put("Nombre", estudiante.nombre)
            arregloDatos.put("Estado Civil", estudiante.estadoCivil)
            return@map arregloDatos
        }
        stringRespuesta += "-----------------------------------------------------------------------------------------\n" +
                "Universidad: ${universidad.nombre}\n" +
                "Estudiantes coinciden: ${nombresEstudiantes}\n"
    }
    stringRespuesta += "-----------------------------------------------------------------------------------------\n" +
            JOptionPane.showMessageDialog(null, stringRespuesta)
}
