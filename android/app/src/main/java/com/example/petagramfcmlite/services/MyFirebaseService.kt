package com.example.petagramfcmlite.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("MyFirebaseService", "Nuevo token: $token")
        // Aquí podrías enviar automáticamente el token al servidor si quieres
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("MyFirebaseService", "Mensaje recibido: ${'$'}{message.data}")
    }
}
