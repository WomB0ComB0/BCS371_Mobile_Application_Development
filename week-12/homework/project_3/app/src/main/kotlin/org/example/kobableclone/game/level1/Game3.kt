package org.example.kobableclone.game.level1

import android.graphics.Point
import org.example.kobableclone.game.base.BaseGame
import org.example.kobableclone.data.model.GameCommand
import java.awt.Point

class Game3 : BaseGame() {
    private val commands = mutableListOf<GameCommand>()
    private var currentPosition = Point(0, 0)
    private val obstacles = mutableListOf<Point>()
    private val goal = Point(4, 4)
    
    override fun initialize() {
        score = 0
        isCompleted = false
        currentPosition = Point(0, 0)
        commands.clear()
        setupObstacles()
    }
    
    private fun setupObstacles() {
        obstacles.apply {
            clear()
            add(Point(1, 1))
            add(Point(2, 2))
            add(Point(3, 3))
            add(Point(2, 3))
            add(Point(3, 2))
        }
    }
    
    fun addCommand(command: GameCommand) {
        commands.add(command)
    }
    
    fun executeCommands() {
        var failed = false
        
        commands.forEach { command ->
            if (!failed) {
                when (command) {
                    GameCommand.MOVE_UP -> failed = !moveUp()
                    GameCommand.MOVE_DOWN -> failed = !moveDown()
                    GameCommand.MOVE_LEFT -> failed = !moveLeft()
                    GameCommand.MOVE_RIGHT -> failed = !moveRight()
                    GameCommand.JUMP -> jumpObstacle()
                    else -> {}
                }
            }
        }
        
        if (!failed) {
            checkCompletion()
        } else {
            playSound(R.raw.game_over)
        }
    }
    
    private fun moveUp(): Boolean {
        if (currentPosition.y < 4 && !hasObstacle(currentPosition.x, currentPosition.y + 1)) {
            currentPosition.y++
            return true
        }
        return false
    }
    
    private fun moveDown(): Boolean {
        if (currentPosition.y > 0 && !hasObstacle(currentPosition.x, currentPosition.y - 1)) {
            currentPosition.y--
            return true
        }
        return false
    }
    
    private fun moveLeft(): Boolean {
        if (currentPosition.x > 0 && !hasObstacle(currentPosition.x - 1, currentPosition.y)) {
            currentPosition.x--
            return true
        }
        return false
    }
    
    private fun moveRight(): Boolean {
        if (currentPosition.x < 4 && !hasObstacle(currentPosition.x + 1, currentPosition.y)) {
            currentPosition.x++
            return true
        }
        return false
    }
    
    private fun jumpObstacle() {
        // Implement jump logic to bypass one obstacle
    }
    
    private fun hasObstacle(x: Int, y: Int): Boolean {
        return obstacles.contains(Point(x, y))
    }
    
    private fun checkCompletion() {
        if (currentPosition == goal) {
            isCompleted = true
            score += 200
            playSound(R.raw.level_complete)
            updateProgress()
        }
    }
}

