
package ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BarraNavegacion(
    pantallaActual: String,
    onNavegar: (String) -> Unit
) {
    val opciones = listOf(
        Triple("Dashboard", "📊", "Inicio"),
        Triple("Pedidos", "📦", "Pedidos"),
        Triple("Catalogo", "🏪", "Catálogo"),
        Triple("Gps", "📍", "GPS"),
        Triple("Perfil", "👤", "Perfil")
    )

    NavigationBar(
        containerColor = Color(0xFF0F172A)
    ) {
        opciones.forEach { (ruta, icono, titulo) ->
            NavigationBarItem(
                selected = pantallaActual == ruta,
                onClick = {
                    onNavegar(ruta)
                },
                icon = {
                    Text(icono)
                },
                label = {
                    Text(titulo)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color(0xFF60A5FA),
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray,
                    indicatorColor = Color(0xFF172A50)
                )
            )
        }
    }
}
