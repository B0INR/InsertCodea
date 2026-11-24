
package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.snapshots.SnapshotStateList

private val fondoPedidos = Color(0xFF080E20)
private val tarjetaPedidos = Color(0xFF111A2D)
private val azulPedidos = Color(0xFF2563EB)

data class Pedido(
    val id: Int,
    val cliente: String,
    val producto: String,
    val precio: Int,
    val estado: String
)

@Composable
fun PedidosScreen(
    onVolver: () -> Unit,
    pedidos: SnapshotStateList<Pedido>,
    productoCotizado: Producto? = null,
    onCotizacionConsumida: () -> Unit = {}
) {


    var mostrarDialogo by remember { mutableStateOf(false) }
    var cliente by remember { mutableStateOf("") }
    var producto by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    LaunchedEffect(productoCotizado) {
        productoCotizado?.let {
            producto = it.nombre
            precio = it.precio.toString()
            mostrarDialogo = true
            onCotizacionConsumida()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoPedidos)
    ) {

        Text(
            text = "Gestión de Pedidos",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Administración de pedidos comerciales",
            color = Color.LightGray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onVolver) {
                Text("← Volver")
            }

            Button(
                onClick = { mostrarDialogo = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = azulPedidos
                )
            ) {
                Text("+ Nuevo pedido")
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(pedidos, key = { it.id }) { pedido ->

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = tarjetaPedidos
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            "PED-${pedido.id.toString().padStart(3, '0')}",
                            color = Color(0xFF94A3B8)
                        )

                        Text(
                            pedido.cliente,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            pedido.producto,
                            color = Color.LightGray
                        )

                        Text(
                            "$" + "%,d".format(pedido.precio)
                                .replace(",", "."),
                            color = Color(0xFF10B981),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            "Estado: ${pedido.estado}",
                            color = when (pedido.estado) {
                                "PENDIENTE" -> Color(0xFFF59E0B)
                                "EN RUTA" -> Color(0xFF60A5FA)
                                else -> Color(0xFF10B981)
                            }
                        )

                        Button(
                            onClick = {
                                val nuevoEstado = when (pedido.estado) {
                                    "PENDIENTE" -> "EN RUTA"
                                    "EN RUTA" -> "ENTREGADO"
                                    else -> "ENTREGADO"
                                }

                                val indice = pedidos.indexOfFirst {
                                    it.id == pedido.id
                                }

                                if (indice >= 0) {
                                    pedidos[indice] = pedido.copy(
                                        estado = nuevoEstado
                                    )
                                }
                            },
                            enabled = pedido.estado != "ENTREGADO"
                        ) {
                            Text("Actualizar estado")
                        }
                    }
                }
            }
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = {
                mostrarDialogo = false
            },
            title = {
                Text("Nuevo pedido")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = cliente,
                        onValueChange = { cliente = it },
                        label = { Text("Cliente") }
                    )

                    OutlinedTextField(
                        value = producto,
                        onValueChange = { producto = it },
                        label = { Text("Producto") }
                    )

                    OutlinedTextField(
                        value = precio,
                        onValueChange = { precio = it },
                        label = { Text("Precio") }
                    )
                }
            },
            confirmButton = {
                Button(
                    enabled = cliente.isNotBlank() &&
                            producto.isNotBlank() &&
                            (precio.toIntOrNull() ?: 0) > 0,
                    onClick = {
                        val nuevoId =
                            (pedidos.maxOfOrNull { it.id } ?: 0) + 1

                        pedidos.add(
                            Pedido(
                                id = nuevoId,
                                cliente = cliente.trim(),
                                producto = producto.trim(),
                                precio = precio.toInt(),
                                estado = "PENDIENTE"
                            )
                        )

                        cliente = ""
                        producto = ""
                        precio = ""
                        mostrarDialogo = false
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarDialogo = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
