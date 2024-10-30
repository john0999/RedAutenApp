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
