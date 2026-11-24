package ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import data.model.Post
import viewmodel.PostViewModel
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    viewModel: PostViewModel,
    onCerrarSesion: () -> Unit,
    onAbrirCamara: () -> Unit,
    onAbrirPerfil: () -> Unit,
    onAbrirGps: () -> Unit,
    onAbrirCatalogo: () -> Unit,
    onAbrirPedidos: () -> Unit,
    pedidos: List<Pedido>,
) {





    val posts by viewModel.postList.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    var menuAbierto by remember { mutableStateOf(false) }
    var editingPost by remember { mutableStateOf<Post?>(null) }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }


    Scaffold(
    topBar = {
        TopAppBar(

            title = {
                Column {
                    Text(
                        text = "InsertCode",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Ventas & Distribución Móvil · DSY1105",
                        color = Color(0xFF94A3B8),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            },

            navigationIcon = {
                Box {
                    IconButton(
                        onClick = {
                            menuAbierto = true
                        }
                    ) {

                        Text(
                            text = "☰",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium
                        )

                    }

                    DropdownMenu(
                        expanded = menuAbierto,
                        onDismissRequest = {
                            menuAbierto = false
                        }
                    ) {

// Opción Perfil
                        DropdownMenuItem(
                            text = {
                                Text("👤 Perfil")
                            },
                            onClick = {
                                menuAbierto = false
                                onAbrirPerfil()
                            }
                        )

                        HorizontalDivider()


                        DropdownMenuItem(
                            text = {
                                Text("📷 Cámara")
                            },
                            onClick = {
                                menuAbierto = false
                                onAbrirCamara()
                            }
                        )


                        DropdownMenuItem(
                            text = {
                                Text("📍 GPS")
                            },
                            onClick = {
                                menuAbierto = false
                                onAbrirGps()
                            }
                        )

                        HorizontalDivider()


                        HorizontalDivider()

                        DropdownMenuItem(
                            text = {
                                Text("Cerrar sesión")
                            },
                            onClick = {
                                menuAbierto = false
                                onCerrarSesion()
                            }
                        )
                    }
                }
            },

                    colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F172A),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )

        )
},





    floatingActionButton = {
            FloatingActionButton(
                onClick = onAbrirPedidos
            ) {
                Text("📦")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF080E20))
        ) {

// Resumen del Dashboard comercial

            DashboardResumen(
                pedidos = pedidos,
                onNuevoPedido = onAbrirPedidos
            )


            if (error != null) {
                Text(
                    text = error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }


            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),

                ) {
                items(posts, key = { it.id }) { post ->

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = post.title,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(post.body)

                            Spacer(Modifier.height(12.dp))

                            Row(
                                horizontalArrangement =
                                    Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        editingPost = post
                                        title = post.title
                                        body = post.body
                                        showDialog = true
                                    }
                                ) {
                                    Text("Editar")
                                }

                                OutlinedButton(
                                    onClick = {
                                        viewModel.deletePost(post.id)
                                    }
                                ) {
                                    Text("Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    if (editingPost == null)
                        "Nueva publicación"
                    else
                        "Editar publicación"
                )
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Título") }
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = body,
                        onValueChange = { body = it },
                        label = { Text("Contenido") }
                    )
                }
            },
            confirmButton = {
                Button(
                    enabled = title.isNotBlank() &&
                            body.isNotBlank(),
                    onClick = {
                        val current = editingPost

                        if (current == null) {
                            viewModel.createPost(title, body)
                        } else {
                            viewModel.updatePost(
                                current.copy(
                                    title = title,
                                    body = body
                                )
                            )
                        }

                        showDialog = false
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}


