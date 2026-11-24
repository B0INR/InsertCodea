package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Producto(
    val codigo: String,
    val tipo: String,
    val nombre: String,
    val descripcion: String,
    val precio: Int
)

@Composable
fun CatalogoScreen(
    onVolver: () -> Unit,
    onCotizar: (Producto) -> Unit
) {
    val productos = listOf(
        Producto(
            "PROD-01",
            "SOFTWARE",
            "Licencia Dashboard Web Sales",
            "Sistema centralizado de ventas, inventario e indicadores.",
            450000
        ),
        Producto(
            "PROD-02",
            "HARDWARE",
            "Módulo IoT Rastreador GPS",
            "Hardware de rastreo vehicular en tiempo real para flotas.",
            120000
        ),
        Producto(
            "PROD-03",
            "HARDWARE",
            "Terminal POS Móvil Android",
            "Dispositivo táctil portátil con lector de comprobantes.",
            180000
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF080E20))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Text(
                text = "Catálogo de Soluciones",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Productos y licencias InsertCode",
                color = Color(0xFF94A3B8)
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onVolver) {
                Text("← Volver al Dashboard")
            }
        }

        items(productos) { producto ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF111A2D)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "${producto.tipo}  ·  ${producto.codigo}",
                        color = Color(0xFF60A5FA),
                        fontSize = 12.sp
                    )

                    Text(
                        text = producto.nombre,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        text = producto.descripcion,
                        color = Color(0xFF94A3B8)
                    )

                    HorizontalDivider(
                        color = Color(0xFF263147)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$" + "%,d".format(
                                producto.precio
                            ).replace(",", "."),
                            color = Color(0xFF10B981),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Button(
                            onClick = { onCotizar(producto) }
                        ) {
                            Text("🛒 Cotizar")
                        }
                    }
                }
            }
        }
    }
}
