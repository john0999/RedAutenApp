package com.example.redautenapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {
            AppContent(auth)
        }
    }
}

@Composable
fun AppContent(auth: FirebaseAuth) {
    var isLoggedIn by remember { mutableStateOf(auth.currentUser != null) }

    if (isLoggedIn) {
        HomeScreen(auth) { isLoggedIn = false }
    } else {
        LoginScreen(auth) { isLoggedIn = true }
    }
}

@Composable
fun LoginScreen(auth: FirebaseAuth, onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Iniciar sesión", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para iniciar sesión
        Button(
            onClick = {
                loading = true
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        loading = false
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                            onLoginSuccess()
                        } else {
                            val errorMessage = task.exception?.message ?: "Error desconocido"
                            Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading
        ) {
            if (loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Iniciar sesión")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para registrarse
        Button(
            onClick = {
                loading = true
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        loading = false
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        } else {
                            val errorMessage = task.exception?.message ?: "Error desconocido"
                            Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading
        ) {
            Text("Registrarse")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Iniciar con una red social", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Twitter
        IconButton(
            onClick = {
                val provider = OAuthProvider.newBuilder("twitter.com")
                loading = true

                auth.startActivityForSignInWithProvider(context as ComponentActivity, provider.build())
                    .addOnCompleteListener { task ->
                        loading = false
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Inicio de sesión con Twitter exitoso", Toast.LENGTH_SHORT).show()
                            onLoginSuccess()
                        } else {
                            val errorMessage = task.exception?.message ?: "Error desconocido"
                            Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            modifier = Modifier.size(48.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo), // Asegúrate de que esto sea el logo correcto
                contentDescription = "Iniciar sesión con Twitter",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun HomeScreen(auth: FirebaseAuth, onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Bienvenido a la Aplicación", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Usuario: ${auth.currentUser?.email ?: "Usuario de Twitter"}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            auth.signOut()
            onLogout()
        }) {
            Text("Cerrar sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(auth = FirebaseAuth.getInstance()) {}
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(auth = FirebaseAuth.getInstance()) {}
}
