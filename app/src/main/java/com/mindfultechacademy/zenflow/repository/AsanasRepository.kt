package com.mindfultechacademy.zenflow.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mindfultechacademy.zenflow.model.Asana
import kotlinx.coroutines.tasks.await

interface AsanasRepository {
    suspend fun getAsanas(): List<Asana>
    var asanas: List<Asana>
}

@Suppress("UNCHECKED_CAST")
class AsanasRepositoryImpl : AsanasRepository {
    override var asanas = List<Asana>(0) { Asana("",0,"", "", "", "", listOf("")) }
    private val db = Firebase.firestore

    override suspend fun getAsanas(): List<Asana> {
        val asanas = db.collection("asanas")
            .get()
            .await()
            .map { document ->
                    Asana(
                        document.id,
                        (document["index"] as Long).toInt(),
                        document["benefits"] as String,
                        document["contraindications"] as String,
                        document["name"] as String,
                        document["photo"] as String,
                        document["steps"] as List<String>)
            }
            .sortedBy { it.index }
        this.asanas = asanas
        return asanas
    }
}