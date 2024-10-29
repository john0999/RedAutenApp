package com.example.redautenapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.redautenapp.ui.screens.home.HomeScreen
import com.example.redautenapp.ui.screens.login.LoginScreen
import com.google.firebase.auth.FirebaseAuth

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
