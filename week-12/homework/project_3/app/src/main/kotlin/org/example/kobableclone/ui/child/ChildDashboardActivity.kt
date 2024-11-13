package org.example.kobableclone.ui.child

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.example.kobableclone.R
import org.example.kobableclone.ui.game.LevelSelectActivity
import org.example.kobableclone.data.repository.ProgressRepository

class ChildDashboardActivity : AppCompatActivity() {
    private lateinit var playButton: Button
    private lateinit var progressRecyclerView: RecyclerView
    private lateinit var logoutButton: Button
    private val progressRepository = ProgressRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_dashboard)

        initializeViews()
        setupListeners()
        loadProgress()
    }

    private fun initializeViews() {
        playButton = findViewById(R.id.playButton)
        progressRecyclerView = findViewById(R.id.progressRecyclerView)
        logoutButton = findViewById(R.id.logoutButton)
    }

    private fun setupListeners() {
        playButton.setOnClickListener {
            startActivity(Intent(this, LevelSelectActivity::class.java))
        }

        logoutButton.setOnClickListener {
            // Clear session and return to login
            finish()
        }
    }

    private fun loadProgress() {
        // Load and display child's progress
        val progress = progressRepository.getProgressByUserId(getCurrentUserId())
        // Setup RecyclerView with progress data
    }
}

