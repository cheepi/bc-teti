package com.example.bcnetworking.di

import com.google.firebase.firestore.FirebaseFirestore

object AppModule {
    val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
}
