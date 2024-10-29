package com.example.redautenapp.mvi.action

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.redautenapp.ui.screens.home.HomeScreen
import com.example.redautenapp.ui.screens.login.LoginScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppActions(auth: FirebaseAuth) {
    var isLoggedIn by remember { mutableStateOf(auth.currentUser != null) }

    if (isLoggedIn) {
        HomeScreen(auth) { isLoggedIn = false }
    } else {
        LoginScreen(auth) { isLoggedIn = true }
    }
}

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
