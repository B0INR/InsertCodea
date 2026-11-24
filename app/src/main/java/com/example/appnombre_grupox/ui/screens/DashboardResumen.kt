package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

private val tarjeta = Color(0xFF111A2D)
private val verde = Color(0xFF10B981)
private val amarillo = Color(0xFFF59E0B)
private val azul = Color(0xFF3B82F6)

@Composable
fun DashboardResumen(pedidos: List<Pedido>, onNuevoPedido: () -> Unit) {
    val pendientes = pedidos.count { it.estado == "PENDIENTE" }
    val enRuta = pedidos.count { it.estado == "EN RUTA" }
    val entregados = pedidos.count { it.estado == "ENTREGADO" }
    val ventas = pedidos.filter { it.estado == "ENTREGADO" }.sumOf { it.precio.toLong() }
    val formato = NumberFormat.getIntegerInstance(Locale("es", "CL"))

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Panel Comercial & Operacional", color = Color.White,
            fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("Indicadores de ventas y entregas", color = Color(0xFF94A3B8), fontSize = 12.sp)
        Button(onClick = onNuevoPedido,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))) {
            Text("+ Nuevo Pedido")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(Modifier.weight(1f)) { TarjetaIndicador("VENTAS ENTREGADAS", "$${formato.format(ventas)}", verde) }
            Box(Modifier.weight(1f)) { TarjetaIndicador("PENDIENTES", pendientes.toString(), amarillo) }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(Modifier.weight(1f)) { TarjetaIndicador("EN RUTA", enRuta.toString(), azul) }
            Box(Modifier.weight(1f)) { TarjetaIndicador("ENTREGADOS", entregados.toString(), verde) }
        }
        Card(colors = CardDefaults.cardColors(containerColor = tarjeta),
            shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Estado de Pedidos en Terreno", color = Color.White, fontWeight = FontWeight.Bold,
                    fontSize = 14.sp)
                Row(Modifier.fillMaxWidth().height(13.dp)) {
                    if (pedidos.isNotEmpty()) {
                        if (pendientes > 0) Box(Modifier.weight(pendientes.toFloat()).fillMaxHeight().background(amarillo))
                        if (enRuta > 0) Box(Modifier.weight(enRuta.toFloat()).fillMaxHeight().background(azul))
                        if (entregados > 0) Box(Modifier.weight(entregados.toFloat()).fillMaxHeight().background(verde))
                    } else Box(Modifier.fillMaxSize().background(Color(0xFF263147)))
                }
                Text("🟡 Pendientes ($pendientes)    🔵 En ruta ($enRuta)", color = Color.LightGray, fontSize = 12.sp)
                Text("🟢 Entregados ($entregados)", color = Color.LightGray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun TarjetaIndicador(titulo: String, valor: String, color: Color) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = tarjeta),
        shape = RoundedCornerShape(14.dp)) {
        Column(Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(titulo, color = Color(0xFF94A3B8), fontSize = 10.sp)
            Text(valor, color = color, fontSize = 19.sp, fontWeight = FontWeight.Bold)
        }
    }
}
