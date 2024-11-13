package org.example.kobableclone.util

import org.example.kobableclone.data.model.Progress
import org.example.kobableclone.data.repository.ProgressRepository

object ProgressLogger {
    private val progressRepository: ProgressRepository = ProgressRepository()
    
    fun logProgress(progress: Progress) {
        progressRepository.saveProgress(progress)
    }
    
    fun getChildProgress(childId: String): List<Progress> {
        return progressRepository.getProgressByUserId(childId)
    }
    
    fun getProgressByLevel(userId: String, level: Int): List<Progress> {
        return progressRepository.getProgressByLevel(userId, level)
    }
}

