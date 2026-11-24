package data.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ui.screens.Pedido

object PedidosStorage {
    private const val ARCHIVO = "insertcode_pedidos"
    private const val CLAVE = "lista"
    private val gson = Gson()

    fun cargar(context: Context): List<Pedido> {
        val texto = context.getSharedPreferences(ARCHIVO, Context.MODE_PRIVATE)
            .getString(CLAVE, null) ?: return listOf(
            Pedido(1, "Empresa Tech Chile Ltd", "Licencia Dashboard Web Sales", 450000, "PENDIENTE"),
            Pedido(2, "Comercial Central", "Terminal POS Móvil Android", 180000, "EN RUTA"),
            Pedido(3, "Cliente InsertCode", "Módulo IoT Rastreador GPS", 120000, "ENTREGADO")
        )
        return runCatching {
            val tipo = object : TypeToken<List<Pedido>>() {}.type
            gson.fromJson<List<Pedido>>(texto, tipo) ?: emptyList()
        }.getOrDefault(emptyList())
    }

    fun guardar(context: Context, pedidos: List<Pedido>) {
        context.getSharedPreferences(ARCHIVO, Context.MODE_PRIVATE).edit()
            .putString(CLAVE, gson.toJson(pedidos)).apply()
    }
}
