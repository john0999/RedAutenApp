package com.example.redautenapp.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

fun saveUserToDatabase(user: FirebaseUser?) {
    user?.let {
        val database = FirebaseFirestore.getInstance()
        val userMap = mapOf(
            "uid" to it.uid,
            "name" to (it.displayName ?: ""),
            "email" to (it.email ?: "")
        )

        database.collection("users").document(it.uid)
            .set(userMap)
            .addOnSuccessListener {
                Log.d("Firebase", "Usuario guardado exitosamente en Firestore")
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error guardando usuario: $e")
            }
    }
}
