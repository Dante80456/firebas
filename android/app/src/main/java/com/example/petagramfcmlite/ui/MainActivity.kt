package com.example.petagramfcmlite.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.petagramfcmlite.R
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    // Cambia la URL por la de tu servidor (Heroku o local con ngrok)
    private val serverUrl = "http://localhost:3000/registrar-usuario"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botón de ejemplo para obtener token y enviar
        findViewById<Button>(R.id.btnReceiveNotif).setOnClickListener {
            obtenerTokenYEnviar()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_receive_notifications -> {
                obtenerTokenYEnviar()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun obtenerTokenYEnviar() {
        // Solicita token FCM
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(this, "Error obteniendo token: ${'$'}{task.exception}", Toast.LENGTH_LONG).show()
                return@addOnCompleteListener
            }
            val token = task.result
            Toast.makeText(this, "Token obtenido: ${'$'}token", Toast.LENGTH_LONG).show()

            // id_usuario_instagram: aquí deberías usar el id del usuario seleccionado en la app (simulado)
            val idUsuarioInstagram = "usuario_demo_123"

            // Enviar al server
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    enviarAlServidor(token, idUsuarioInstagram)
                    launch(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Token enviado al servidor", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    launch(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Error enviando token: ${'$'}{e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun enviarAlServidor(token: String, idUsuarioInstagram: String) {
        val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()

        val json = "{" +
                "\"id_dispositivo\":\"${'$'}token\"," +
                "\"id_usuario_instagram\":\"${'$'}idUsuarioInstagram\"}"

        val body = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(serverUrl)
            .post(body)
            .build()

        client.newCall(request).execute().use { resp ->
            if (!resp.isSuccessful) throw Exception("HTTP ${'$'}{resp.code}: ${'$'}{resp.message}")
        }
    }
}
