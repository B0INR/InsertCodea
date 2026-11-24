package data.model

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.security.MessageDigest
import java.security.SecureRandom

/** Registro local para prototipo académico. No es un servicio de autenticación de producción. */
object RegistroUsuarios {
    private const val ARCHIVO = "insertcode_cuentas_locales"
    private const val CLAVE = "cuentas"

    private fun registros(context: Context): JSONArray = runCatching {
        JSONArray(context.getSharedPreferences(ARCHIVO, Context.MODE_PRIVATE).getString(CLAVE, "[]"))
    }.getOrElse { JSONArray() }

    private fun hash(clave: String, sal: String): String {
        val bytes = MessageDigest.getInstance("SHA-256")
            .digest((sal + ":" + clave).toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun registrar(context: Context, nombre: String, correo: String, clave: String): String? {
        val email = correo.trim().lowercase()
        if (nombre.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return "Ingresa un nombre y correo válidos"
        if (clave.length < 6) return "La contraseña debe tener al menos 6 caracteres"
        if (UsuariosDemo.usuarios.any { it.correo.equals(email, true) } ||
            buscarCorreo(context, email) != null) return "El correo ya está registrado"
        val salBytes = ByteArray(16).also { SecureRandom().nextBytes(it) }
        val sal = salBytes.joinToString("") { "%02x".format(it) }
        val lista = registros(context)
        lista.put(JSONObject().put("nombre", nombre.trim()).put("correo", email)
            .put("sal", sal).put("hash", hash(clave, sal)).put("rol", "VENDEDOR"))
        context.getSharedPreferences(ARCHIVO, Context.MODE_PRIVATE).edit()
            .putString(CLAVE, lista.toString()).apply()
        return null
    }

    private fun buscarCorreo(context: Context, correo: String): JSONObject? {
        val lista = registros(context)
        for (i in 0 until lista.length()) {
            val usuario = lista.getJSONObject(i)
            if (usuario.optString("correo").equals(correo, true)) return usuario
        }
        return null
    }

    fun autenticar(context: Context, correo: String, clave: String): Usuario? {
        UsuariosDemo.validarLogin(correo, clave)?.let { return it }
        val item = buscarCorreo(context, correo.trim().lowercase()) ?: return null
        if (hash(clave, item.optString("sal")) != item.optString("hash")) return null
        return Usuario(item.optString("nombre"), item.optString("correo"), "", item.optString("rol"))
    }
}
