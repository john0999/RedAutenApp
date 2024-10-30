package com.example.redautenapp.mvi.action

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.redautenapp.data.repository.saveUserToDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider

// Artemio

fun handleLogin(auth: FirebaseAuth, email: String, password: String, context: Context, onSuccess: () -> Unit) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                saveUserToDatabase(auth.currentUser)
                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                onSuccess()
            } else {
                val errorMessage = task.exception?.message ?: "Error desconocido"
                Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        }
}

fun handleRegistration(auth: FirebaseAuth, email: String, password: String, context: Context, onSuccess: () -> Unit) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                saveUserToDatabase(auth.currentUser)
                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                onSuccess()
            } else {
                val errorMessage = task.exception?.message ?: "Error desconocido"
                Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        }
}

fun handleTwitterLogin(auth: FirebaseAuth, context: Context, onSuccess: () -> Unit) {
    val provider = OAuthProvider.newBuilder("twitter.com")
    auth.startActivityForSignInWithProvider(context as ComponentActivity, provider.build())
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                saveUserToDatabase(auth.currentUser)
                Toast.makeText(context, "Inicio de sesión con Twitter exitoso", Toast.LENGTH_SHORT).show()
                onSuccess()
            } else {
                val errorMessage = task.exception?.message ?: "Error desconocido"
                Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        }
}
