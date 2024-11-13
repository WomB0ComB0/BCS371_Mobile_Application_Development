package org.example.kobableclone.ui.parent

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.example.kobableclone.R
import org.example.kobableclone.data.repository.UserRepository

class ParentDashboardActivity : AppCompatActivity() {
    private lateinit var addChildButton: Button
    private lateinit var childrenRecyclerView: RecyclerView
    private lateinit var logoutButton: Button
    private val userRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_dashboard)

        initializeViews()
        setupListeners()
        loadChildren()
    }

    private fun initializeViews() {
        addChildButton = findViewById(R.id.addChildButton)
        childrenRecyclerView = findViewById(R.id.childrenRecyclerView)
        logoutButton = findViewById(R.id.logoutButton)
    }

    private fun setupListeners() {
        addChildButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java).apply {
                putExtra("isChild", true)
                putExtra("parentId", getCurrentUserId())
            })
        }

        logoutButton.setOnClickListener {
            // Clear session and return to login
            finish()
        }
    }

    private fun loadChildren() {
        val children = userRepository.getChildrenByParentId(getCurrentUserId())
        // Setup RecyclerView with children data
    }
}

