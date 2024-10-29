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

    Josafat HomeScreen.kt

            @Composable
            fun HomeScreen(auth: FirebaseAuth, onLogout: () -> Unit) {
                val currentUser = auth.currentUser
                val userName = currentUser?.displayName ?: "Usuario"
                val userEmail = currentUser?.email ?: "Correo no disponible"

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Bienvenido a la Aplicación", style = MaterialTheme.typography.headlineMedium)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Nombre de usuario: $userName")
                    Text("Correo: $userEmail")

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = {
                        auth.signOut()
                        onLogout()
                    }) {
                        Text("Cerrar sesión")
                    }
                }
            }
}



