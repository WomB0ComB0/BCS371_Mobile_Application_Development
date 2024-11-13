package org.example.kobableclone.game.level2

import android.graphics.Point
import org.example.kobableclone.game.base.BaseGame
import org.example.kobableclone.data.model.GameCommand
import java.awt.Point

class Game1 : BaseGame() {
    private val commands = mutableListOf<GameCommand>()
    private var currentPosition = Point(0, 0)
    private val keys = mutableListOf<Point>()
    private val doors = mutableListOf<Point>()
    private val goal = Point(4, 4)
    private var collectedKeys = 0
    
    override fun initialize() {
        score = 0
        isCompleted = false
        currentPosition = Point(0, 0)
        commands.clear()
        collectedKeys = 0
        setupLevel()
    }
    
    private fun setupLevel() {
        keys.apply {
            clear()
            add(Point(1, 1))
            add(Point(3, 1))
        }
        
        doors.apply {
            clear()
            add(Point(2, 3))
            add(Point(3, 4))
        }
    }
    
    fun executeCommands() {
        commands.forEach { command ->
            when (command) {
                GameCommand.MOVE_UP -> moveUp()
                GameCommand.MOVE_DOWN -> moveDown()
                GameCommand.MOVE_LEFT -> moveLeft()
                GameCommand.MOVE_RIGHT -> moveRight()
                GameCommand.COLLECT -> collectKey()
                else -> {}
            }
        }
        checkCompletion()
    }
    
    private fun moveUp() {
        if (currentPosition.y < 4 && canMove(currentPosition.x, currentPosition.y + 1)) {
            currentPosition.y++
            checkKey()
        }
    }
    
    private fun moveDown() {
        if (currentPosition.y > 0 && canMove(currentPosition.x, currentPosition.y - 1)) {
            currentPosition.y--
            checkKey()
        }
    }
    
    private fun moveLeft() {
        if (currentPosition.x > 0 && canMove(currentPosition.x - 1, currentPosition.y)) {
            currentPosition.x--
            checkKey()
        }
    }
    
    private fun moveRight() {
        if (currentPosition.x < 4 && canMove(currentPosition.x + 1, currentPosition.y)) {
            currentPosition.x++
            checkKey()
        }
    }
    
    private fun canMove(x: Int, y: Int): Boolean {
        val targetPoint = Point(x, y)
        return !doors.contains(targetPoint) || collectedKeys > 0
    }
    
    private fun checkKey() {
        if (keys.contains(currentPosition)) {
            collectKey()
        }
    }
    
    private fun collectKey() {
        val iterator = keys.iterator()
        while (iterator.hasNext()) {
            val key = iterator.next()
            if (currentPosition == key) {
                iterator.remove()
                collectedKeys++
                score += 50
                playSound(R.raw.collect_key)
            }
        }
    }
    
    private fun checkCompletion() {
        if (currentPosition == goal) {
            isCompleted = true
            score += 300
            playSound(R.raw.level_complete)
            updateProgress()
        }
    }
}

