

Instrucciones rápidas:
1. Android: abrir `android` en Android Studio. Reemplaza `google-services.json` en `android/app/` con el archivo que obtengas de Firebase Console.
   - Configura el packageName en Firebase igual que `com.example.petagramfcmlite`.
   - Añade tu `google-services.json` en `android/app/` antes de compilar.
2. Server: para ejecutar localmente:
   - `cd server`
   - `npm install`
   - `node index.js`
   - El servidor escuchará en puerto 3000 por defecto.
   - Para desplegar en Heroku, añade tu Git remote y push. Procfile incluido.
3. En la app: usa el menú -> "Recibir Notificaciones" para obtener tu token FCM y enviarlo al endpoint `/registrar-usuario`.

Notas de seguridad y despliegue:
- No incluyo `google-services.json` por razones de privacidad. Debes obtenerlo en https://console.firebase.google.com/.
- Para Heroku, usa variables de entorno y habilita el addon de SQLite (o mejor: Postgres en producción).
