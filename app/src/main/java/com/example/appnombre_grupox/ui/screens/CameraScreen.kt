package ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import ui.screens.CameraScreen

@Composable
fun CameraScreen(
    onVolver: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var permisoCamara by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val solicitarPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        permisoCamara = concedido
    }

    LaunchedEffect(Unit) {
        if (!permisoCamara) {
            solicitarPermiso.launch(Manifest.permission.CAMERA)
        }
    }

    val cameraController = remember {
        LifecycleCameraController(context)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = onVolver,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("← Volver a publicaciones")
        }

        if (permisoCamara) {

            DisposableEffect(lifecycleOwner) {
                cameraController.bindToLifecycle(lifecycleOwner)

                onDispose {
                    cameraController.unbind()
                }
            }

            AndroidView(
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        controller = cameraController
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

        } else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Necesitamos permiso para usar la cámara."
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        solicitarPermiso.launch(
                            Manifest.permission.CAMERA
                        )
                    }
                ) {
                    Text("Solicitar permiso")
                }
            }
        }
    }
}
