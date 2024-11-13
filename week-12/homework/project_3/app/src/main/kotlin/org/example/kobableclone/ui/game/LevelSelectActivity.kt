package org.example.kobableclone.ui.game

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.kobableclone.R
import org.example.kobableclone.data.model.Game
import org.example.kobableclone.data.repository.ProgressRepository

class LevelSelectActivity : AppCompatActivity() {
    private lateinit var levelsRecyclerView: RecyclerView
    private val progressRepository = ProgressRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_select)

        initializeViews()
        setupLevels()
    }

    private fun initializeViews() {
        levelsRecyclerView = findViewById(R.id.levelsRecyclerView)
        levelsRecyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun setupLevels() {
        val levels = listOf(
            Game("1", 1, "Basic Movement", "Learn to move around", 1, listOf()),
            Game("2", 1, "Collecting Items", "Collect items while moving", 1, listOf()),
            Game("3", 1, "Avoiding Obstacles", "Navigate through obstacles", 2, listOf()),
            Game("4", 2, "Keys and Doors", "Use keys to open doors", 2, listOf()),
            Game("5", 2, "Teleporters", "Use teleporters to move", 3, listOf()),
            Game("6", 2, "Moving Obstacles", "Avoid moving obstacles", 3, listOf())
        )

        // Implement LevelAdapter and set it to RecyclerView
    }
}

