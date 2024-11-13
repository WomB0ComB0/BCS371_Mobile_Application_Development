package org.example.kobableclone.game.level2

import android.graphics.Point
import org.example.kobableclone.game.base.BaseGame
import org.example.kobableclone.data.model.GameCommand
import java.awt.Point

class Game2 : BaseGame() {
    private val commands = mutableListOf<GameCommand>()
    private var currentPosition = Point(0, 0)
    private val teleporters = mutableMapOf<Point, Point>()
    private val switches = mutableListOf<Point>()
    private val barriers = mutableListOf<Point>()
    private val goal = Point(4, 4)
    private var activatedSwitches = 0
    
    override fun initialize() {
        score = 0
        isCompleted = false
        currentPosition = Point(0, 0)
        commands.clear()
        activatedSwitches = 0
        setupLevel()
    }
    
    private fun setupLevel() {
        teleporters.apply {
            clear()
            put(Point(1, 1), Point(3, 3))
            put(Point(2, 2), Point(4, 1))
        }
        
        switches.apply {
            clear()
            add(Point(1, 3))
            add(Point(3, 1))
        }
        
        barriers.apply {
            clear()
            add(Point(2, 4))
            add(Point(4, 2))
        }
    }
    
    fun executeCommands() {
        commands.forEach { command ->
            when (command) {
                GameCommand.MOVE_UP -> moveUp()
                GameCommand.MOVE_DOWN -> moveDown()
                GameCommand.MOVE_LEFT -> moveLeft()
                GameCommand.MOVE_RIGHT -> moveRight()
                GameCommand.COLLECT -> activateSwitch()
                else -> {}
            }
            checkTeleporter()
        }
        checkCompletion()
    }
    
    private fun moveUp() {
        if (currentPosition.y < 4 && !isBarrier(currentPosition.x, currentPosition.y + 1)) {
            currentPosition.y++
            checkSwitch()
        }
    }
    
    private fun moveDown() {
        if (currentPosition.y > 0 && !isBarrier(currentPosition.x, currentPosition.y - 1)) {
            currentPosition.y--
            checkSwitch()
        }
    }
    
    private fun moveLeft() {
        if (currentPosition.x > 0 && !isBarrier(currentPosition.x - 1, currentPosition.y)) {
            currentPosition.x--
            checkSwitch()
        }
    }
    
    private fun moveRight() {
        if (currentPosition.x < 4 && !isBarrier(currentPosition.x + 1, currentPosition.y)) {
            currentPosition.x++
            checkSwitch()
        }
    }
    
    private fun isBarrier(x: Int, y: Int): Boolean {
        return barriers.contains(Point(x, y)) && activatedSwitches < switches.size
    }
    
    private fun checkTeleporter() {
        teleporters[currentPosition]?.let { destination ->
            currentPosition = Point(destination.x, destination.y)
            playSound(R.raw.teleport)
        }
    }
    
    private fun checkSwitch() {
        if (switches.contains(currentPosition)) {
            activateSwitch()
        }
    }
    
    private fun activateSwitch() {
        if (switches.contains(currentPosition) && !switches.contains(currentPosition)) {
            activatedSwitches++
            score += 75
            playSound(R.raw.switch_activated)
        }
    }
    
    private fun checkCompletion() {
        if (currentPosition == goal && activatedSwitches == switches.size) {
            isCompleted = true
            score += 400
            playSound(R.raw.level_complete)
            updateProgress()
        }
    }
}

