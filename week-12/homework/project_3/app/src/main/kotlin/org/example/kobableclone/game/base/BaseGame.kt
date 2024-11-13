package org.example.kobableclone.game.base

import org.example.kobableclone.data.model.Progress
import org.example.kobableclone.util.AudioManager
import org.example.kobableclone.util.ProgressLogger
import java.util.*

abstract class BaseGame {
    protected var score: Int = 0
    protected var isCompleted: Boolean = false
    protected lateinit var audioManager: AudioManager
    
    abstract fun initialize()
    abstract fun start()
    abstract fun pause()
    abstract fun resume()
    abstract fun end()
    
    protected fun playSound(soundId: Int) {
        audioManager.playSound(soundId)
    }
    
    protected fun updateProgress() {
        ProgressLogger.logProgress(
            Progress(
                id = UUID.randomUUID().toString(),
                userId = getCurrentUserId(), // TODO: Implement this
                gameId = getGameId(), // TODO: Implement this
                level = getCurrentLevel(), // TODO: Implement this
                score = score,
                completionTime = getElapsedTime() // TODO: Implement this
            )
        )
    }
}

