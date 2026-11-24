package data.model

data class Usuario(
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val rol: String
)

object UsuariosDemo {

    val usuarios = listOf(
        Usuario(
            nombre = "Administrador",
            correo = "admin@pro.test",
            contrasena = "123456",
            rol = "ADMIN"
        ),

        Usuario(
            nombre = "Vendedor Terreno",
            correo = "vendedor@insertcode.cl",
            contrasena = "123456",
            rol = "VENDEDOR"
        ),

        Usuario(
            nombre = "Supervisor",
            correo = "supervisor@insertcode.cl",
            contrasena = "123456",
            rol = "SUPERVISOR"
        ),

        Usuario(
            nombre = "Repartidor",
            correo = "repartidor@insertcode.cl",
            contrasena = "123456",
            rol = "REPARTIDOR"
        ),

        Usuario(
            nombre = "Operador",
            correo = "operador@insertcode.cl",
            contrasena = "123456",
            rol = "OPERADOR"
        )
    )

    fun validarLogin(
        correo: String,
        contrasena: String
    ): Usuario? {
        return usuarios.find {
            it.correo.equals(
                correo.trim(),
                ignoreCase = true
            ) && it.contrasena == contrasena
        }
    }
}
