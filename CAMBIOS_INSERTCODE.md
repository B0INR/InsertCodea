# InsertCode Android - Cambios integrados

## Corregido
- Barra de navegación compartida en `MainActivity.kt`; se conserva al abrir Pedidos, Catálogo, GPS, Perfil y Cámara.
- Dashboard con cuatro indicadores y barra de estados proporcionales a los pedidos.
- Botón Nuevo Pedido y botón flotante del Dashboard: ahora abren Pedidos.
- Botón Cotizar: ahora abre la creación de un pedido con producto y precio precargados.
- Pedidos compartidos entre pantallas y persistidos localmente en SharedPreferences (JSON).
- Perfil muestra nombre, correo y rol del usuario autenticado.
- Registro local básico de cuentas, con sal aleatoria y SHA-256 para contraseñas registradas.
- Se conservan las cuentas de demostración existentes, CameraX, GPS y Retrofit.

## Pendiente de infraestructura externa
El botón «Continuar con Google» informa que falta configurar Firebase Authentication / Credential Manager; **no inicia sesión con Google todavía**. No se añadió autenticación ficticia. Para habilitarla se requiere un proyecto Firebase, configurar el proveedor Google y las credenciales de Android.

## Probar en Android Studio
1. Abrir la carpeta del proyecto, esperar Gradle Sync.
2. Compilar `Build > Make Project`.
3. Iniciar con `admin@pro.test` / `123456` (solo demostración).
4. Navegar desde la barra inferior a Pedidos, Catálogo, GPS, Perfil y volver.
5. Crear un pedido, modificarlo de Pendiente a En Ruta y Entregado. Verificar que las cifras y barra del Dashboard cambian.
6. Cotizar un producto del Catálogo; comprobar que se abre el formulario de pedido con el precio y producto.
7. Cerrar y abrir la app, comprobar persistencia de pedidos.
8. Probar `Crear una cuenta` y acceder con la nueva cuenta local.

## Limitación de verificación
No se pudo realizar compilación Android en el entorno de edición porque Gradle 9.8.2 no está disponible localmente y no se permite descargarla. Se ejecutaron verificaciones estáticas; realizar Sync/Build en Android Studio para comprobar la compilación real.

## Seguridad
Los usuarios de prueba permanecen codificados en `Usuario.kt`. El registro nuevo es local y académico; no equivale a autenticación segura en servidor. No publicar secretos, claves de firma ni archivos `secrets.properties`.
