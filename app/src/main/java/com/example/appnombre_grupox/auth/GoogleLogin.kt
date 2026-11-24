package auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.appnombre_grupox.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import data.model.Usuario
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/** Inicio de sesión real: cuenta Google -> token ID -> Firebase Authentication. */
object GoogleLogin {
    suspend fun iniciarSesion(context: Context): Usuario {
        // Recurso generado desde google-services.json por el plugin Google Services.
        val webClientId = context.getString(R.string.default_web_client_id)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(webClientId)
            .setFilterByAuthorizedAccounts(false) // Permite crear cuenta con Google en primer acceso.
            .build()
        val solicitud = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        val respuesta = CredentialManager.create(context).getCredential(
            context = context,
            request = solicitud
        )
        val credencial = respuesta.credential
        if (credencial !is CustomCredential || credencial.type != TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            error("No se recibió una credencial de Google válida")
        }
        val token = GoogleIdTokenCredential.createFrom(credencial.data).idToken
        val firebaseCredential = GoogleAuthProvider.getCredential(token, null)
        val firebaseUser = suspendCancellableCoroutine<FirebaseUser> { continuation ->
            FirebaseAuth.getInstance().signInWithCredential(firebaseCredential)
                .addOnCompleteListener { task ->
                    if (!continuation.isActive) return@addOnCompleteListener
                    val usuario = if (task.isSuccessful) task.result?.user else null
                    if (task.isSuccessful && usuario != null) {
                        continuation.resume(usuario)
                    } else {
                        continuation.resumeWithException(
                            task.exception ?: IllegalStateException("Falló la autenticación Firebase")
                        )
                    }
                }
        }
        // Los usuarios registrados por Google se crean con el rol mínimo, nunca ADMIN.
        return Usuario(
            nombre = firebaseUser.displayName ?: "Usuario Google",
            correo = firebaseUser.email ?: "",
            contrasena = "", // Firebase administra la credencial, no se almacena una contraseña.
            rol = "VENDEDOR"
        )
    }
}
