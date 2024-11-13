package org.example.kobableclone.game.level1

import android.graphics.Point
import org.example.kobableclone.game.base.BaseGame
import org.example.kobableclone.data.model.GameCommand
import java.awt.Point
import java.util.*

class Game2 : BaseGame() {
    private val commands = mutableListOf<GameCommand>()
    private var currentPosition = Point(0, 0)
    private val collectibles = mutableListOf<Point>()
    private var collectedItems = 0
    
    override fun initialize() {
        score = 0
        isCompleted = false
        currentPosition = Point(0, 0)
        commands.clear()
        collectedItems = 0
        setupCollectibles()
    }
    
    private fun setupCollectibles() {
        collectibles.apply {
            clear()
            add(Point(2, 2))
            add(Point(3, 1))
            add(Point(1, 3))
        }
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
                GameCommand.MOVE_UP -> moveUp()
                GameCommand.MOVE_DOWN -> moveDown()
                GameCommand.MOVE_LEFT -> moveLeft()
                GameCommand.MOVE_RIGHT -> moveRight()
                GameCommand.COLLECT -> checkCollectible()
                else -> {}
            }
        }
        checkCompletion()
    }
    
    private fun moveUp() {
        if (currentPosition.y < 4) {
            currentPosition.y++
            checkCollectible()
        }
    }
    
    private fun moveDown() {
        if (currentPosition.y > 0) {
            currentPosition.y--
            checkCollectible()
        }
    }
    
    private fun moveLeft() {
        if (currentPosition.x > 0) {
            currentPosition.x--
            checkCollectible()
        }
    }
    
    private fun moveRight() {
        if (currentPosition.x < 4) {
            currentPosition.x++
            checkCollectible()
        }
    }
    
    private fun checkCollectible() {
        val iterator = collectibles.iterator()
        while (iterator.hasNext()) {
            val collectible = iterator.next()
            if (currentPosition == collectible) {
                iterator.remove()
                collectedItems++
                score += 50
                playSound(R.raw.collect_item)
            }
        }
    }
    
    private fun checkCompletion() {
        if (collectibles.isEmpty()) {
            isCompleted = true
            score += 100
            playSound(R.raw.level_complete)
            updateProgress()
        }
    }
}

