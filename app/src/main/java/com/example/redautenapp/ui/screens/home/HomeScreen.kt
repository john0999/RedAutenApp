package com.example.redautenapp.ui.screens.home
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