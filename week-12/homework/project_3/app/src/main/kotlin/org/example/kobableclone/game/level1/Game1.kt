package org.example.kobableclone.game.level1

import org.example.kobableclone.game.base.BaseGame
import org.example.kobableclone.data.model.Point
import org.example.kobableclone.data.model.GameCommand
import org.example.kobableclone.util.AudioManager
import java.awt.Point

class Game1 : BaseGame() {
    private val commands = mutableListOf<GameCommand>()
    private var currentPosition = Point(0, 0)
    private val goal = Point(5, 5)
    
    override fun initialize() {
        score = 0
        isCompleted = false
        currentPosition = Point(0, 0)
        commands.clear()
        audioManager = AudioManager(
            context = applicationContext,
            soundIds = listOf(R.raw.game_start, R.raw.success)
        )
    }
    
    override fun start() {
        playSound(R.raw.game_start)
    }
    
    fun addCommand(command: GameCommand) {
        commands.add(command)
    }
    
    fun executeCommands() {
        commands.forEach { command ->
            when (command) {
                GameCommand.MOVE_UP -> currentPosition.y++
                GameCommand.MOVE_DOWN -> currentPosition.y--
                GameCommand.MOVE_LEFT -> currentPosition.x--
                GameCommand.MOVE_RIGHT -> currentPosition.x++
                else -> {} // Handle other commands
            }
            
            checkGoal()
        }
    }
    
    private fun checkGoal() {
        if (currentPosition == goal) {
            isCompleted = true
            score += 100
            playSound(R.raw.success)
            updateProgress()
        }
    }
}

