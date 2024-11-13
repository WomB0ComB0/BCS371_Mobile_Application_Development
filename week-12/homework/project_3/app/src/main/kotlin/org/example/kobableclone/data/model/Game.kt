package org.example.kobableclone.data.model

data class Game(
    val id: String,
    val level: Int,
    val name: String,
    val description: String,
    val difficulty: Int,
    val commands: List<GameCommand>
)

enum class GameCommand {
    MOVE_UP,
    MOVE_DOWN,
    MOVE_LEFT,
    MOVE_RIGHT,
    JUMP,
    COLLECT
}

