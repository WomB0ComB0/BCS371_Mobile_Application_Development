package org.example.kobableclone.ui.parent

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import org.example.kobableclone.R
import org.example.kobableclone.data.repository.ProgressRepository

class ChildProgressActivity : AppCompatActivity() {
    private lateinit var childNameText: TextView
    private lateinit var progressChart: LineChart
    private lateinit var averageScoreText: TextView
    private lateinit var completedLevelsText: TextView
    private val progressRepository = ProgressRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_progress)

        val childId = intent.getStringExtra("childId") ?: return finish()
        
        initializeViews()
        loadChildProgress(childId)
    }

    private fun initializeViews() {
        childNameText = findViewById(R.id.childNameText)
        progressChart = findViewById(R.id.progressChart)
        averageScoreText = findViewById(R.id.averageScoreText)
        completedLevelsText = findViewById(R.id.completedLevelsText)
    }

    private fun loadChildProgress(childId: String) {
        val progress = progressRepository.getProgressByUserId(childId)
        
        // Setup progress chart
        // Update statistics
        // Display completed levels
    }
}

