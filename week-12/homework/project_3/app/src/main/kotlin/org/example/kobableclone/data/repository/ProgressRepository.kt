package org.example.kobableclone.data.repository

import org.example.kobableclone.data.local.DatabaseHelper
import org.example.kobableclone.data.model.Progress
import java.util.*

class ProgressRepository {
    private val dbHelper = DatabaseHelper.getInstance()

    fun saveProgress(progress: Progress) {
        dbHelper.writableDatabase.use { db ->
            val values = ContentValues().apply {
                put("id", progress.id)
                put("user_id", progress.userId)
                put("game_id", progress.gameId)
                put("level", progress.level)
                put("score", progress.score)
                put("completion_time", progress.completionTime)
                put("timestamp", progress.timestamp)
            }
            
            db.insert("progress", null, values)
        }
    }

    fun getProgressByUserId(userId: String): List<Progress> {
        val progressList = mutableListOf<Progress>()
        
        dbHelper.readableDatabase.use { db ->
            val cursor = db.query(
                "progress",
                null,
                "user_id = ?",
                arrayOf(userId),
                null,
                null,
                "timestamp DESC"
            )

            cursor.use {
                while (it.moveToNext()) {
                    progressList.add(
                        Progress(
                            id = it.getLong(it.getColumnIndex("id")),
                            userId = it.getString(it.getColumnIndex("user_id")),
                            gameId = it.getString(it.getColumnIndex("game_id")),
                            level = it.getInt(it.getColumnIndex("level")),
                            score = it.getInt(it.getColumnIndex("score")),
                            completionTime = it.getLong(it.getColumnIndex("completion_time")),
                            timestamp = it.getLong(it.getColumnIndex("timestamp"))
                        )
                    )
                }
            }
        }

        return progressList
    }

    fun getProgressByLevel(userId: String, level: Int): List<Progress> {

    }
}

