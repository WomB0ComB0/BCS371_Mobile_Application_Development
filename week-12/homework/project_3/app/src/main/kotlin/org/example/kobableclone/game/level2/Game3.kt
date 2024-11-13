package org.example.kobableclone.game.level2

import android.graphics.Point
import org.example.kobableclone.game.base.BaseGame
import org.example.kobableclone.data.model.GameCommand
import java.awt.Point

class Game3 : BaseGame() {
    private val commands = mutableListOf<GameCommand>()
    private var currentPosition = Point(0, 0)
    private val movingObstacles = mutableListOf<MovingObstacle>()
    private val powerUps = mutableListOf<Point>()
    private val goal = Point(4, 4)
    private var hasPowerUp = false
    private var moveCount = 0
    
    data class MovingObstacle(
        var position: Point,
        val pattern: List<Point>,
        var currentIndex: Int = 0
    )
    
    override fun initialize() {
        score = 0
        isCompleted = false
        currentPosition = Point(0, 0)
        commands.clear()
        hasPowerUp = false
        moveCount = 0
        setupLevel()
    }
    
    private fun setupLevel() {
        movingObstacles.apply {
            clear()
            add(MovingObstacle(
                Point(2, 2),
                listOf(Point(2, 2), Point(2, 3), Point(3, 3), Point(3, 2))
            ))
            add(MovingObstacle(
                Point(1, 4),
                listOf(Point(1, 4), Point(2, 4), Point(3, 4))
            ))
        }
        
        powerUps.apply {
            clear()
            add(Point(1, 1))
            add(Point(3, 1))
        }
    }
    
    fun executeCommands() {
        commands.forEach { command ->
            moveObstacles()
            
            when (command) {
                GameCommand.MOVE_UP -> moveUp()
                GameCommand.MOVE_DOWN -> moveDown()
                GameCommand.MOVE_LEFT -> moveLeft()
                GameCommand.MOVE_RIGHT -> moveRight()
                GameCommand.COLLECT -> collectPowerUp()
                else -> {}
            }
            
            if (checkCollision() && !hasPowerUp) {
                playSound(R.raw.game_over)
                return
            }
            
            moveCount++
            if (moveCount % 10 == 0) {
                hasPowerUp = false
            }
        }
        checkCompletion()
    }
    
    private fun moveObstacles() {
        movingObstacles.forEach { obstacle ->
            obstacle.currentIndex = (obstacle.currentIndex + 1) % obstacle.pattern.size
            obstacle.position = obstacle.pattern[obstacle.currentIndex]
        }
    }
    
    private fun moveUp() {
        if (currentPosition.y < 4) {
            currentPosition.y++
            checkPowerUp()
        }
    }
    
    private fun moveDown() {
        if (currentPosition.y > 0) {
            currentPosition.y--
            checkPowerUp()
        }
    }
    
    private fun moveLeft() {
        if (currentPosition.x > 0) {
            currentPosition.x--
            checkPowerUp()
        }
    }
    
    private fun moveRight() {
        if (currentPosition.x < 4) {
            currentPosition.x++
            checkPowerUp()
        }
    }
    
    private fun checkCollision(): Boolean {
        return movingObstacles.any { it.position == currentPosition }
    }
    
    private fun checkPowerUp() {
        if (powerUps.contains(currentPosition)) {
            collectPowerUp()
        }
    }
    
    private fun collectPowerUp() {
        if (powerUps.contains(currentPosition)) {
            powerUps.remove(currentPosition)
            hasPowerUp = true
            score += 100
            playSound(R.raw.power_up)
        }
    }
    
    private fun checkCompletion() {
        if (currentPosition == goal) {
            isCompleted = true
            score += 500
            playSound(R.raw.level_complete)
            updateProgress()
        }
    }
}

