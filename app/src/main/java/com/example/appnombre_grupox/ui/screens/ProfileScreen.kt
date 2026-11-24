package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.Usuario

@Composable
fun ProfileScreen(
    onVolver: () -> Unit,
    usuario: Usuario
) {
    val fondo = Color(0xFF080E20)
    val tarjeta = Color(0xFF111A2D)
    val azul = Color(0xFF2563EB)
    val gris = Color(0xFF94A3B8)
    val verde = Color(0xFF9C27B0)

    var editando by remember {
        mutableStateOf(false)
    }

    var nombre by remember {
        mutableStateOf(usuario.nombre)
    }

    var correo by remember {
        mutableStateOf(usuario.correo)
    }

    var telefono by remember {
        mutableStateOf("+56 9 8765 4321")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "Mi Perfil Corporativo",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Text(
            text = "Información de cuenta y permisos asignados",
            color = gris,
            fontSize = 13.sp
        )

        Button(
            onClick = {
                editando = !editando
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E293B)
            )
        ) {
            Text(
                if (editando) "Cancelar edición"
                else "✏️ Editar Perfil"
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = tarjeta
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "💼",
                    fontSize = 52.sp
                )

                if (editando) {

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        label = { Text("Correo") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { telefono = it },
                        label = { Text("Teléfono") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            editando = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = azul
                        )
                    ) {
                        Text("Guardar cambios")
                    }

                } else {

                    Text(
                        text = nombre,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = usuario.rol,
                        color = Color(0xFF60A5FA),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "✉️ $correo",
                        color = gris
                    )

                    Text(
                        text = "📍 Sede Alameda - Santiago",
                        color = gris
                    )

                    Text(
                        text = "📞 $telefono",
                        color = gris
                    )

                    HorizontalDivider(
                        color = Color(0xFF263147)
                    )

                    Text(
                        text = "Cuenta de demostración",
                        color = verde,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = tarjeta
            )
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "🛡️ Permisos del Perfil (${usuario.rol})",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                PermisoPerfil(
                    "Consulta de catálogo de soluciones y clientes"
                )

                PermisoPerfil(
                    "Registro y cotización de nuevos pedidos"
                )

                PermisoPerfil(
                    "Consulta de estados de despacho"
                )
            }
        }

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = azul
            )
        ) {
            Text("← Volver al Dashboard")
        }
    }
}

@Composable
private fun PermisoPerfil(
    nombre: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFF080E20),
                RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = nombre,
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "✓ Activo",
            color = Color(0xFF10B981),
            fontSize = 12.sp
        )
    }
}
