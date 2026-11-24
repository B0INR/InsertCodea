package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import data.model.RegistroUsuarios

@Composable
fun RegistroScreen(onVolverLogin: () -> Unit) {
    val context = LocalContext.current
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var repetir by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var exito by remember { mutableStateOf(false) }
    Column(
        Modifier.fillMaxSize().background(Color(0xFF080E20))
            .verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Crear cuenta InsertCode", color = Color.White, style = MaterialTheme.typography.headlineMedium)
        Text("Registro local de demostración (rol: VENDEDOR)", color = Color.LightGray)
        OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre completo") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        OutlinedTextField(correo, { correo = it }, label = { Text("Correo electrónico") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        OutlinedTextField(clave, { clave = it }, label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), singleLine = true)
        OutlinedTextField(repetir, { repetir = it }, label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), singleLine = true)
        if (error.isNotBlank()) Text(error, color = Color(0xFFFF7777))
        if (exito) Text("Cuenta creada. Ya puedes iniciar sesión.", color = Color(0xFF10B981))
        Button(
            onClick = {
                if (clave != repetir) {
                    error = "Las contraseñas no coinciden"
                } else {
                    error = RegistroUsuarios.registrar(context, nombre, correo, clave) ?: ""
                    exito = error.isEmpty()
                }
            },
            enabled = !exito,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) { Text("Crear cuenta") }
        OutlinedButton(onClick = onVolverLogin, modifier = Modifier.fillMaxWidth()) {
            Text(if (exito) "Ir a iniciar sesión" else "Volver al inicio de sesión")
        }
        Text("Para crear cuentas en otros dispositivos se necesita un servidor o Firebase.", color = Color.LightGray)
    }
}
