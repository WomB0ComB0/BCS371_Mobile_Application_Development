package org.example.kobableclone.data.model

data class Progress(
    val id: String,
    val userId: String,
    val gameId: String,
    val level: Int,
    val score: Int,
    val completionTime: Long,
    val timestamp: Long = System.currentTimeMillis()
)

