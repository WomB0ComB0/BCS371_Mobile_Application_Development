package org.example.kobableclone.ui.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.example.kobableclone.R
import org.example.kobableclone.ui.child.ChildDashboardActivity

class GameCompletionActivity : AppCompatActivity() {
    private lateinit var scoreTextView: TextView
    private lateinit var nextLevelButton: Button
    private lateinit var retryButton: Button
    private lateinit var homeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_completion)

        val score = intent.getIntExtra("score", 0)
        val isCompleted = intent.getBooleanExtra("isCompleted", false)

        initializeViews()
        setupUI(score, isCompleted)
        setupListeners()
    }

    private fun initializeViews() {
        scoreTextView = findViewById(R.id.scoreTextView)
        nextLevelButton = findViewById(R.id.nextLevelButton)
        retryButton = findViewById(R.id.retryButton)
        homeButton = findViewById(R.id.homeButton)
    }

    private fun setupUI(score: Int, isCompleted: Boolean) {
        scoreTextView.text = "Score: $score"
        nextLevelButton.isEnabled = isCompleted
    }

    private fun setupListeners() {
        nextLevelButton.setOnClickListener {
            val nextLevel = intent.getIntExtra("currentLevel", 1) + 1
            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra("level", nextLevel)
                putExtra("gameId", 1)
            }
            startActivity(intent)
            finish()
        }

        retryButton.setOnClickListener {
            finish()
        }

        homeButton.setOnClickListener {
            startActivity(Intent(this, ChildDashboardActivity::class.java))
            finishAffinity()
        }
    }
}

