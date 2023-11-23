package com.mindfultechacademy.zenflow.model

data class Asana(
    val id: String,
    val index: Int,
    val benefits: String,
    val contraindications: String,
    val name: String,
    val photo: String,
    val steps: List<String>
)
