# InsertCode: activar el inicio de sesión con Google

El código ya usa Credential Manager y FirebaseAuth, pero la autenticación **no funcionará** sin completar la configuración de Firebase. En particular, el plugin google-services necesita el archivo `app/google-services.json` para compilar.

1. Abre https://console.firebase.google.com/ y crea o selecciona un proyecto.
2. Agrega una aplicación Android con `applicationId` **com.example.appnombre_grupox** (ver `app/build.gradle.kts`).
3. En Android Studio, abre la terminal y ejecuta `./gradlew signingReport` (en PowerShell: `.\gradlew.bat signingReport`). Copia el SHA-1 de debug en **Firebase > Configuración del proyecto > Tus apps > Huellas digitales**. Para APK firmada agrega también el SHA-1 del keystore release.
4. En **Firebase > Authentication > Sign-in method**, habilita **Google** y configura el correo de asistencia del proyecto.
5. Descarga de nuevo el `google-services.json` actualizado **después** de activar Google. Colócalo en `app/google-services.json` (no en la carpeta raíz). Asegúrate de que tenga la configuración OAuth para el cliente web (`default_web_client_id`).
6. Sincroniza Gradle, compila y ejecuta en un emulador con Google Play Services o en un teléfono. Pulsa **Continuar con Google**.
7. Si el proveedor no muestra cuentas, comprueba Google Play Services, SHA-1, coincidencia del package name y el JSON actualizado.

Los usuarios locales anteriores todavía son demostrativos. El perfil creado desde Google recibe `VENDEDOR` por defecto y no adquiere privilegios de administrador. Firebase no almacena los roles de la aplicación: para autorización real configura roles/claims y reglas en backend; no confíes solo en la cadena `rol` local.

No subas `.jks`, `secrets.properties` o credenciales privadas. `google-services.json` está excluido aquí del repositorio por precaución. Las Firebase config API keys no suelen ser secretos de servidor, pero deben gestionarse y restringirse correctamente.

No se ejecutó una compilación completa Android en el entorno de edición; verifica Android Studio en tu equipo.
