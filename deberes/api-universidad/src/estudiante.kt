class Estudiante(
    val nombre: String,
    val estadoCivil: ESTADO_CIVIL,
    val tieneEnfermedad: Boolean
) {
    enum class ESTADO_CIVIL {
        soltero,
        casado,
        viudo,
        divorsiado
    }
}